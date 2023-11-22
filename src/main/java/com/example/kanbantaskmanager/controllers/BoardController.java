package com.example.kanbantaskmanager.controllers;

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

import com.example.kanbantaskmanager.dtos.BoardDto;
import com.example.kanbantaskmanager.dtos.ColorUpdateDto;
import com.example.kanbantaskmanager.dtos.CreateBoardDto;
import com.example.kanbantaskmanager.dtos.CreateStatusDto;
import com.example.kanbantaskmanager.dtos.CreateTaskDto;
import com.example.kanbantaskmanager.dtos.TaskDto;
import com.example.kanbantaskmanager.mappers.BoardMapper;
import com.example.kanbantaskmanager.mappers.TaskMapper;
import com.example.kanbantaskmanager.models.Board;
import com.example.kanbantaskmanager.models.Task;
import com.example.kanbantaskmanager.services.BoardService;
import com.example.kanbantaskmanager.services.StatusService;
import com.example.kanbantaskmanager.services.TaskService;

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

    @GetMapping("/{boardId}")
    public BoardDto getOneBoard(@PathVariable Long boardId) {
        Board searchedBoard = boardService.getBoardById(boardId);
        return boardMapper.convertToDto(searchedBoard);
    }

    @PostMapping()
    public BoardDto createBoard(@RequestBody CreateBoardDto boardDto) {
        return this.boardService.createBoard(boardDto);
    }

    @PutMapping("/{id}")
    public BoardDto updateBoard(@RequestBody BoardDto boardDto, @PathVariable Long id) {
        boardService.updateBoard(boardDto, id);
        return boardDto;
    }

    @DeleteMapping("/{id}")
    public void removeBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }

    // Status Codes
    @GetMapping("/{boardId}/status_codes")
    public List<CreateStatusDto> getAllByBoardId(@PathVariable Long boardId) {
        return statusService.findAllByBoardId(boardId);
    }

    @PostMapping("/{boardId}/status_codes")
    public CreateStatusDto create(@RequestBody CreateStatusDto statusDto, @PathVariable Long boardId) {
        return this.statusService.createStatus(statusDto, boardId);
    }

    @PutMapping("/{boardId}/status_codes/{statusId}")
    public CreateStatusDto update(@PathVariable Long boardId, @RequestBody CreateStatusDto statusDto,
            @PathVariable Long statusId) {
        statusService.updateStatus(boardId, statusDto, statusId);
        return statusDto;
    }

    @PutMapping("/{boardId}/status_codes/{statusId}/color")
    public CreateStatusDto updateStatusColor(
            @PathVariable Long boardId,
            @PathVariable Long statusId,
            @RequestBody ColorUpdateDto colorUpdateDto) {
        String newColor = colorUpdateDto.getNewColor();
        return statusService.updateStatusColor(boardId, statusId, newColor);
    }

    @DeleteMapping("/{boardId}/status_codes/{statusId}")
    public void deleteStatus(@PathVariable Long boardId, @PathVariable Long statusId) {
        statusService.deleteStatus(statusId);
    }

    // Tasks

    @GetMapping("/{boardId}/tasks")
    public List<TaskDto> getTasksByBoard(@PathVariable Long boardId) {
        List<TaskDto> tasksByBoard = taskService.getTaskByBoardId(boardId);
        return tasksByBoard;
    }

    // add error handling when task with particular id exists, but not in provided
    // board...
    @GetMapping("/{boardId}/tasks/{taskId}")
    public TaskDto getTaskFromBoard(@PathVariable Long boardId, @PathVariable Long taskId) {
        Task searchedTask = taskService.getTaskById(taskId);
        return taskMapper.convertToDto(searchedTask);
    }

    @PostMapping("/{boardId}/tasks")
    public TaskDto createTask(@RequestBody CreateTaskDto taskDto, @PathVariable Long boardId) {
        return this.taskService.createTask(taskDto, boardId);
    }

    @PutMapping("/{boardId}/tasks/{taskId}")
    public TaskDto updateTask(@PathVariable Long boardId, @RequestBody TaskDto taskDto, @PathVariable Long taskId) {
        taskService.updateTask(boardId, taskDto, taskId);
        return taskDto;
    }

    @DeleteMapping("/{boardId}/tasks/{taskId}")
    public void removeTaskFromBoard(@PathVariable Long boardId, @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}
