package com.example.kanbantaskmanager.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kanbantaskmanager.dtos.CreateTaskDto;
import com.example.kanbantaskmanager.dtos.TaskDto;
import com.example.kanbantaskmanager.mappers.TaskMapper;
import com.example.kanbantaskmanager.models.Board;
import com.example.kanbantaskmanager.repositories.BoardRepository;
import com.example.kanbantaskmanager.models.Status;
import com.example.kanbantaskmanager.models.Subtask;
import com.example.kanbantaskmanager.models.Task;
import com.example.kanbantaskmanager.repositories.SubtaskRepository;
import com.example.kanbantaskmanager.repositories.TaskRepository;

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
    private SubtaskRepository subtaskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public List<TaskDto> findAll() {
        List<Task> tasksList = this.taskRepository.findAll();
        List<TaskDto> tasksDtos = tasksList.stream().map((task) -> taskMapper.convertToDto(task)).toList();
        return tasksDtos;
    }

    public List<TaskDto> getTaskByBoardId(Long boardId) {
        List<Task> tasksByBoardId = taskRepository.findByBoardId(boardId);

        if (!boardRepository.existsById(boardId)) {
            throw new EntityNotFoundException("Board with id " + boardId + " not found");
        }

        List<TaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasksByBoardId) {
            taskDtos.add(taskMapper.convertToDto(task));
        }
        return taskDtos;
    }

    public TaskDto createTask(CreateTaskDto taskDto, Long boardId) {
        taskDto.setBoardId(boardId);
        Task newTask = taskMapper.convertToEntity(taskDto);
        Task savedTask = this.taskRepository.save(newTask);

        if (taskDto.getSubtasks() != null && taskDto.getSubtasks().size() != 0) {
            List<Subtask> subtasksToSave = taskDto.getSubtasks().stream().map((title) -> {
                Subtask subtask = new Subtask(title, false);
                subtask.setTask(savedTask);
                return subtask;
            }).collect(Collectors.toList());
            List<Subtask> savedSubtasks = subtaskRepository.saveAll(subtasksToSave);
            savedTask.setSubtasks(savedSubtasks);
        }
        return taskMapper.convertToDto(savedTask);
    }

    public Set<Task> findAllById(Set<Long> ids) {
        return new HashSet<Task>(this.taskRepository.findAllById(ids));
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Transactional
    public void deleteTask(Long id) {
        subtaskRepository.deleteByTaskId(id);
        taskRepository.deleteById(id);
    }

    public TaskDto updateTask(Long boardId, TaskDto taskDto, Long taskId) {
        Task taskToUpdate = this.getTaskById(taskId);
        if (taskToUpdate == null) {
            throw new EntityNotFoundException("Task with id " + taskId + " does not exist");
        }
        taskToUpdate.setId(taskId);
        taskToUpdate.setTitle(taskDto.getTitle());
        taskToUpdate.setDescription(taskDto.getDescription());

        Status status = statusService.getStatusById(taskDto.getStatusId());
        taskToUpdate.setStatus(status);

        Board board = boardService.getBoardById(boardId);
        taskToUpdate.setBoard(board);
        taskRepository.save(taskToUpdate);
        return taskMapper.convertToDto(taskToUpdate);
    }

    public TaskDto updateStatusOnDrag(Long boardId, Long taskId, Long statusId) {
        Task taskToUpdate = this.getTaskById(taskId);
        Board board = boardService.getBoardById(boardId);
        Status newStatus = statusService.getStatusById(statusId);
        taskToUpdate.setId(taskId);
        taskToUpdate.setBoard(board);
        taskToUpdate.setDescription(taskToUpdate.getDescription());
        taskToUpdate.setStatus(newStatus);
        taskToUpdate.setTitle(taskToUpdate.getTitle());
        taskRepository.save(taskToUpdate);
        return taskMapper.convertToDto(taskToUpdate);
    }
}
