package com.example.kanbantaskmanager.Board;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.Column.Column;
import com.example.kanbantaskmanager.Column.ColumnService;

@Service
public class BoardMapper {
    @Autowired
    private ColumnService columnService;

    public Board convertToEntity(CreateBoardDto boardDto) {
        Board newBoard = new Board();
        Set<Column> columns = columnService.findAllById(boardDto.getColumnIds());

        newBoard.setName(boardDto.getName());
        newBoard.setColumns(columns);

        return newBoard;
    }
}
