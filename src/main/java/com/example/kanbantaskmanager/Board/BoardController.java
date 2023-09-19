package com.example.kanbantaskmanager.Board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kanbantaskmanager.Status.CreateStatusDto;
import com.example.kanbantaskmanager.Status.StatusService;
import com.example.kanbantaskmanager.Task.CreateTaskDto;
import com.example.kanbantaskmanager.Task.Task;
import com.example.kanbantaskmanager.Task.TaskMapper;
import com.example.kanbantaskmanager.Task.TaskService;

@RestController
@RequestMapping("boards")
@CrossOrigin(origins = "http://localhost:5173")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private BoardMapper boardMapper;

    @GetMapping
    public List<BoardDto> getAllBoards() {
        return this.boardService.findAll();
    }

     @GetMapping("/{boardId}/tasks")
    public List<CreateTaskDto> getTasksByBoard(@PathVariable Long boardId) {
        List<CreateTaskDto> tasksByBoard = taskService.getTaskByBoardId(boardId);
        return tasksByBoard;
    }

    @GetMapping("/{boardId}")
    public BoardDto getOneBoard(@PathVariable Long boardId) {
        Board searchedBoard = boardService.getBoardById(boardId);
        return boardMapper.convertToDto(searchedBoard);
    }
// add error handling when task with particular id exists, but not in provided board...
    @GetMapping("/{boardId}/tasks/{taskId}")
    public CreateTaskDto getTaskFromBoard(@PathVariable Long boardId, @PathVariable Long taskId) {
        Task searchedTask = taskService.getTaskById(taskId);
        return taskMapper.convertToDto(searchedTask);
    }
    
    @GetMapping("/{boardId}/status_codes")
    public List<CreateStatusDto> getAllByBoardId(@PathVariable Long boardId) {
        return statusService.findAllByBoardId(boardId);
    }
    @PostMapping()
    public BoardDto createBoard(@RequestBody BoardDto boardDto) {
        return this.boardService.createBoard(boardDto);
    }

    @PostMapping("/{boardId}/tasks")
    public CreateTaskDto createTask(@RequestBody CreateTaskDto taskDto, @PathVariable Long boardId) {
        return this.taskService.createTask(taskDto, boardId);
    }

    @DeleteMapping("/{id}")
    public void removeBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }
     
    @DeleteMapping("/{boardId}/tasks/{taskId}")
    public void removeTaskFromBoard(@PathVariable Long boardId, @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        }

    @PutMapping("/{id}")
    public BoardDto updateBoard(@RequestBody BoardDto boardDto, @PathVariable Long id) {
        boardService.updateBoard(boardDto, id);
        return boardDto;
    }

    @PutMapping("/{boardId}/tasks/{taskId}")
    public CreateTaskDto updateTask(@PathVariable Long boardId, @RequestBody CreateTaskDto taskDto, @PathVariable Long taskId) {
        taskService.updateTask(boardId, taskDto, taskId);
        return taskDto;
    }
}
