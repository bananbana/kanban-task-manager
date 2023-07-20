package com.example.kanbantaskmanager.Board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    
    public List<Board> findAll() {
        return this.boardRepository.findAll();
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).get();
    }

    public Board createBoard(Board board) {
        return this.boardRepository.save(board);
        }
    
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public void updateBoard(Board board, Long id) {
        boardRepository.save(board);
    }
}