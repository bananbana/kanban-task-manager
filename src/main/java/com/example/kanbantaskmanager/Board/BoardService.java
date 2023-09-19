package com.example.kanbantaskmanager.Board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.Status.StatusRepository;
import com.example.kanbantaskmanager.Subtask.SubtaskRepository;
import com.example.kanbantaskmanager.Task.Task;
import com.example.kanbantaskmanager.Task.TaskRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private SubtaskRepository subtaskRepository;
    @Autowired
    private StatusRepository statusRepository;

    public List<BoardDto> findAll() {
        List<Board> boardsList = this.boardRepository.findAll();
        List<BoardDto> boardDtos = boardsList.stream().map((board) -> boardMapper.convertToDto(board)).toList();
        return boardDtos;
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
        .orElseThrow(() -> new EntityNotFoundException("Board with id " + boardId + " not found"));
    }

    public BoardDto createBoard(BoardDto boardDto) {
        Board newBoard = boardMapper.convertToEntity(boardDto);
        Board savedBoard = this.boardRepository.save(newBoard);

        return boardMapper.convertToDto((savedBoard));
    }
    @Transactional
    public void deleteBoard(Long boardId) {
        //Delete subtasks associated with tasks belonging to the board
        List<Task> tasks = taskRepository.findByBoardId(boardId);
            for (Task task : tasks) {
                subtaskRepository.deleteByTaskId(task.getId());
            }

        //Delete tasks associated with the board
        taskRepository.deleteByBoardId(boardId);
        //Delete status codes associated with the board
        statusRepository.deleteByBoardId(boardId);
        //Delete the board itself
        boardRepository.deleteById(boardId);
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Board with id {} has been removed", boardId);

    }

    public BoardDto updateBoard(BoardDto boardDto, Long id) {
        Board boardToUpdate = this.getBoardById(id);
        boardToUpdate.setName(boardDto.getName());
        boardRepository.save(boardToUpdate);
        return boardMapper.convertToDto(boardToUpdate);

    }
}