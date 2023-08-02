package com.example.kanbantaskmanager.Board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired BoardMapper boardMapper;
    
    public List<Board> findAll() {
        return this.boardRepository.findAll();
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).get();
    }

    public CreateBoardDto createBoard(CreateBoardDto boardDto) {
Board newBoard = boardMapper.convertToEntity(boardDto);
Board savedBoard = this.boardRepository.save(newBoard);

        return boardMapper.convertToDto((savedBoard));
        }
    
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public void updateBoard(Board board, Long id) {
        boardRepository.save(board);
    }
}