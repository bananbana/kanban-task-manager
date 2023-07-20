package com.example.kanbantaskmanager.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/boards/{id}")
    public Board getOne(@PathVariable("id") Long id) {
        return boardService.getBoardById(id);
    }

    //post customer details to database
    @PostMapping("/boards")
    public Board create(@RequestBody Board board) {
        return this.boardService.createBoard(board);
    }

    //delete specific customer
    @DeleteMapping("/boards/{id}")
    public void removeBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }

    //update customer details in database
    @PutMapping("/boards/{id}")
    public Board update(@RequestBody Board board, @PathVariable Long id) {
        boardService.updateBoard(board, id);
        return board;
    }
}
