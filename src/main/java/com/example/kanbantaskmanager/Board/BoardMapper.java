package com.example.kanbantaskmanager.Board;

import org.springframework.stereotype.Service;

@Service
public class BoardMapper {

    public CreateBoardDto convertToDto(Board board) {
        CreateBoardDto newBoard = new CreateBoardDto();
        newBoard.setId(board.getId());
        newBoard.setName(board.getName());

        return newBoard;
    }
    
public Board convertToEntity(BoardDto boardDto) {
        Board newBoard = new Board();

        newBoard.setName(boardDto.getName());
        newBoard.setId(boardDto.getId());

        return newBoard;
    }

    public Board convertToEntity(CreateBoardDto boardDto) {
        Board newBoard = new Board();

        newBoard.setName(boardDto.getName());

        return newBoard;
    }
}
