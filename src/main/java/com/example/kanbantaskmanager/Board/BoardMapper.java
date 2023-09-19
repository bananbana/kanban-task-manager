package com.example.kanbantaskmanager.Board;

import org.springframework.stereotype.Service;

@Service
public class BoardMapper {
    public BoardDto convertToDto(Board board) {
        BoardDto newBoard = new BoardDto();
        newBoard.setId(board.getId());
        newBoard.setName(board.getName());

        return newBoard;
    }

    public Board convertToEntity(BoardDto boardDto) {
        Board newBoard = new Board();
        newBoard.setId(boardDto.getId());
        newBoard.setName(boardDto.getName());

        return newBoard;
    }
}
