package com.example.kanbantaskmanager.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.dtos.CreateStatusDto;
import com.example.kanbantaskmanager.models.Board;
import com.example.kanbantaskmanager.models.Status;
import com.example.kanbantaskmanager.services.BoardService;

@Service
public class StatusMapper {
    @Autowired
    private BoardService boardService;

    public CreateStatusDto convertToDto(Status status) {
        CreateStatusDto newStatus = new CreateStatusDto();
        newStatus.setId(status.getId());
        newStatus.setName(status.getName());
        newStatus.setBoardId(status.getBoard().getId());
        newStatus.setColor(status.getColor());

        return newStatus;
    }

    public Status convertToEntity(CreateStatusDto statusDto) {
        Board board = boardService.getBoardById(statusDto.getBoardId());
        Status newStatus = new Status();
        newStatus.setName(statusDto.getName());
        newStatus.setBoard(board);
        newStatus.setColor(statusDto.getColor());

        return newStatus;
    }
}
