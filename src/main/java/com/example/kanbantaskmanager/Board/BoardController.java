package com.example.kanbantaskmanager.Board;

import java.util.List;

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

    @GetMapping("/boards")
    public List<Board> getAll() {
        return this.boardService.findAll();
    }

    @GetMapping("/boards/{id}")
    public Board getOne(@PathVariable("id") Long id) {
        return boardService.getBoardById(id);
    }

    @PostMapping("/boards")
    public CreateBoardDto create(@RequestBody CreateBoardDto boardDto) {
        return this.boardService.createBoard(boardDto);
    }

    @DeleteMapping("/boards/{id}")
    public void removeBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }

    @PutMapping("/boards/{id}")
    public Board update(@RequestBody Board board, @PathVariable Long id) {
        boardService.updateBoard(board, id);
        return board;
    }
}
