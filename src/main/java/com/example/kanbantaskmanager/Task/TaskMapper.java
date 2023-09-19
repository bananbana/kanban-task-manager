package com.example.kanbantaskmanager.Task;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.Subtask.Subtask;
import com.example.kanbantaskmanager.Board.Board;
import com.example.kanbantaskmanager.Board.BoardService;
import com.example.kanbantaskmanager.Status.Status;
import com.example.kanbantaskmanager.Status.StatusService;

@Service
public class TaskMapper {
    @Autowired
    private StatusService statusService;
    @Autowired
    private BoardService boardService;

    public CreateTaskDto convertToDto(Task searchedTask) {
        CreateTaskDto newTask = new CreateTaskDto();
        List<Long> subtaskIds = searchedTask.getSubtasks().stream().map(Subtask::getId).collect(Collectors.toList());
        newTask.setDescription(searchedTask.getDescription());
        newTask.setId(searchedTask.getId());
        newTask.setStatusId(searchedTask.getStatus().getId());
        newTask.setTitle(searchedTask.getTitle());
        newTask.setBoardId(searchedTask.getBoard().getId());
        newTask.setSubtasks(subtaskIds);

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
