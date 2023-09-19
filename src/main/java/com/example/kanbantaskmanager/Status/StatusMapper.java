package com.example.kanbantaskmanager.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.Board.Board;
import com.example.kanbantaskmanager.Board.BoardService;

@Service
public class StatusMapper {
    @Autowired
    private BoardService boardService;

    public CreateStatusDto convertToDto(Status status) {
        CreateStatusDto newStatus = new CreateStatusDto();
        newStatus.setId(status.getId());
        newStatus.setName(status.getName());
        newStatus.setBoardId(status.getBoard().getId());

        return newStatus;
    }

    public Status convertToEntity(CreateStatusDto statusDto) {
        Board board = boardService.getBoardById(statusDto.getBoardId());
        Status newStatus = new Status();
        newStatus.setName(statusDto.getName());
        newStatus.setBoard(board);

        return newStatus;
    }
}
