package com.example.kanbantaskmanager.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.models.Subtask;
import com.example.kanbantaskmanager.dtos.CreateTaskDto;
import com.example.kanbantaskmanager.dtos.TaskDto;
import com.example.kanbantaskmanager.models.Board;
import com.example.kanbantaskmanager.services.BoardService;
import com.example.kanbantaskmanager.models.Status;
import com.example.kanbantaskmanager.models.Task;

import com.example.kanbantaskmanager.services.StatusService;

@Service
public class TaskMapper {
    @Autowired
    private StatusService statusService;
    @Autowired
    private BoardService boardService;

    public TaskDto convertToDto(Task searchedTask) {
        TaskDto newTask = new TaskDto();
        List<Long> subtaskIds = searchedTask.getSubtasks() != null
                ? searchedTask.getSubtasks().stream().map(Subtask::getId).collect(Collectors.toList())
                : null;
        List<Long> completedSubtasksIds = searchedTask.getSubtasks() != null
                ? searchedTask.getSubtasks().stream()
                        .filter(Subtask::getIsCompleted)
                        .map(Subtask::getId)
                        .collect(Collectors.toList())
                : null;
        newTask.setDescription(searchedTask.getDescription());
        newTask.setId(searchedTask.getId());
        newTask.setStatusId(searchedTask.getStatus().getId());
        newTask.setTitle(searchedTask.getTitle());
        newTask.setBoardId(searchedTask.getBoard().getId());
        newTask.setSubtasks(subtaskIds);
        newTask.setCompletedSubtasks(completedSubtasksIds);

        return newTask;
    }

    public Task convertToEntity(CreateTaskDto taskDto) {
        Task newTask = new Task();
        Status status = statusService.getStatusById(taskDto.getStatusId());
        Long boardId = taskDto.getBoardId();
        if (boardId == null) {
            throw new IllegalArgumentException("Board ID must not be null");
        }

        Board board = boardService.getBoardById(boardId);
        newTask.setTitle(taskDto.getTitle());
        newTask.setDescription(taskDto.getDescription());
        newTask.setStatus(status);
        newTask.setBoard(board);

        return newTask;
    }
}
