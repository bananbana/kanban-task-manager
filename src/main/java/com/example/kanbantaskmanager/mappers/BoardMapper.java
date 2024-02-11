package com.example.kanbantaskmanager.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.dtos.BoardDto;
import com.example.kanbantaskmanager.dtos.CreateBoardDto;
import com.example.kanbantaskmanager.models.Board;
import com.example.kanbantaskmanager.models.Status;
import com.example.kanbantaskmanager.models.User;
import com.example.kanbantaskmanager.models.Task;

import com.example.kanbantaskmanager.repositories.StatusRepository;
import com.example.kanbantaskmanager.repositories.UserRepository;

@Service
public class BoardMapper {

    @Autowired
    UserRepository userRepository;
    @Autowired
    StatusRepository statusRepository;

    public BoardDto convertToDto(Board board) {
        BoardDto newBoard = new BoardDto();
        List<Long> statusIds = board.getStatus().stream().map(Status::getId).collect(Collectors.toList());
        List<Long> taskIds = board.getTasks().stream().map(Task::getId).collect(Collectors.toList());
        List<Long> userIds = board.getUsers().stream().map(User::getId).collect(Collectors.toList());

        newBoard.setId(board.getId());
        newBoard.setName(board.getName());
        newBoard.setOwnerId(board.getOwner().getId());
        newBoard.setStatusCodes(statusIds);
        newBoard.setTasks(taskIds);
        newBoard.setUserIds(userIds);

        return newBoard;
    }

    public CreateBoardDto convertToCreateBoardDto(Board board) {
        CreateBoardDto newCreateBoard = new CreateBoardDto();
        List<String> statusTitles = board.getStatus().stream().map(Status::getName).collect(Collectors.toList());
        List<Long> userIds = board.getUsers().stream().map(User::getId).collect(Collectors.toList());

        newCreateBoard.setId(board.getId());
        newCreateBoard.setName(board.getName());
        newCreateBoard.setOwnerId(board.getOwner().getId());
        newCreateBoard.setStatusCodes(statusTitles);
        newCreateBoard.setUserIds(userIds);

        return newCreateBoard;
    }

    public Board convertToEntity(BoardDto boardDto) {
        Board newBoard = new Board();
        newBoard.setId(boardDto.getId());
        newBoard.setName(boardDto.getName());
        newBoard.setOwner(userRepository.findById(boardDto.getOwnerId()).orElseThrow());

        return newBoard;
    }

    public Board convertToCreateEntity(CreateBoardDto createBoardDto) {
        Board newBoard = new Board();
        List<Status> statusCodes = statusRepository.findAllByBoardId(createBoardDto.getId());
        newBoard.setId(createBoardDto.getId());
        newBoard.setName(createBoardDto.getName());
        newBoard.setOwner(userRepository.findById(createBoardDto.getOwnerId()).orElseThrow());
        newBoard.setStatus(statusCodes);

        return newBoard;
    }
}
