package com.example.kanbantaskmanager.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.Board.Board;
import com.example.kanbantaskmanager.Board.BoardRepository;
import com.example.kanbantaskmanager.Board.BoardService;
import com.example.kanbantaskmanager.Status.Status;
import com.example.kanbantaskmanager.Status.StatusService;

import jakarta.persistence.EntityNotFoundException;


@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private StatusService statusService;
    @Autowired
    private BoardService boardService;

    @Autowired
    private TaskMapper taskMapper;

    public List<CreateTaskDto> findAll() {
       List<Task> tasksList = this.taskRepository.findAll();
       List<CreateTaskDto> tasksDtos = tasksList.stream().map((task) -> taskMapper.convertToDto(task)).toList();
       return tasksDtos; 
    }

    public List<CreateTaskDto> getTaskByBoardId(Long boardId) {
        List<Task> tasksByBoardId = taskRepository.findByBoardId(boardId);

        if (!boardRepository.existsById(boardId)) {
            throw new EntityNotFoundException("Board with id " + boardId + " not found");
        }

        List<CreateTaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasksByBoardId) {
            taskDtos.add(taskMapper.convertToDto(task));
        }
        return taskDtos;
    }

    public CreateTaskDto createTask(CreateTaskDto taskDto, Long boardId) {
        taskDto.setBoardId(boardId);
        Task newTask = taskMapper.convertToEntity(taskDto);
        Task savedTask = this.taskRepository.save(newTask);
        return taskMapper.convertToDto(savedTask);
    }

    public Set<Task> findAllById(Set<Long> ids) {
        return new HashSet<Task>(this.taskRepository.findAllById(ids));
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public CreateTaskDto updateTask(Long boardId, CreateTaskDto taskDto, Long id) {
        Task taskToUpdate = this.getTaskById(id);
            if (taskToUpdate == null) {
                throw new EntityNotFoundException("Task with id " + id + " does not exist");
            }
        taskToUpdate.setTitle(taskDto.getTitle());
        taskToUpdate.setDescription(taskDto.getDescription());

        Status status = statusService.getStatusById(taskDto.getStatusId());
        taskToUpdate.setStatus(status);

        Board board = boardService.getBoardById(boardId);
        taskToUpdate.setBoard(board);
        taskRepository.save(taskToUpdate);
        return taskMapper.convertToDto(taskToUpdate);
    }
}
