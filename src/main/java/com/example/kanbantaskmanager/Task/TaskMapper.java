package com.example.kanbantaskmanager.Task;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.Board.Board;
import com.example.kanbantaskmanager.Board.BoardService;
import com.example.kanbantaskmanager.Status.Status;
import com.example.kanbantaskmanager.Status.StatusService;
import com.example.kanbantaskmanager.Subtask.Subtask;
import com.example.kanbantaskmanager.Subtask.SubtaskService;

@Service
public class TaskMapper {
    @Autowired
    private SubtaskService subtaskService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private BoardService boardService;

    public CreateTaskDto convertToDto(Task task) {
        CreateTaskDto newTask = new CreateTaskDto();
        newTask.setDescription(task.getDescription());
        newTask.setId(task.getId());
        newTask.setStatusId(task.getStatus().getId());
        newTask.setSubtasks(task.getSubtasks().stream().map(Subtask::getId).collect(Collectors.toSet()));
        newTask.setTitle(task.getTitle());
        newTask.setBoard(task.getBoard().getId());

        return newTask;
    }

    public Task convertToEntity(CreateTaskDto taskDto) {
        Task newTask = new Task();
        Status status = statusService.getStatusById(taskDto.getStatusId());
        Set<Subtask> subtasks = subtaskService.findAllById(taskDto.getSubtasks());
        Board boardId = boardService.getBoardById(taskDto.getBoard());

        newTask.setTitle(taskDto.getTitle());
        newTask.setDescription(taskDto.getDescription());
        newTask.setStatus(status);
        newTask.setSubtasks(subtasks);
        newTask.setBoard(boardId);

        return newTask;
    }
}
