package com.example.kanbantaskmanager.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.dtos.BoardDto;
import com.example.kanbantaskmanager.dtos.CreateBoardDto;
import com.example.kanbantaskmanager.models.Board;
import com.example.kanbantaskmanager.models.Status;

@Service
public class BoardMapper {

    public BoardDto convertToDto(Board board) {
        BoardDto newBoard = new BoardDto();
        List<Long> statusIds = board.getStatus().stream().map(Status::getId).collect(Collectors.toList());
        newBoard.setId(board.getId());
        newBoard.setName(board.getName());
        newBoard.setStatusCodes(statusIds);

        return newBoard;
    }

    public Board convertToEntity(CreateBoardDto boardDto) {
        Board newBoard = new Board();
        newBoard.setId(boardDto.getId());
        newBoard.setName(boardDto.getName());

        return newBoard;
    }
}
