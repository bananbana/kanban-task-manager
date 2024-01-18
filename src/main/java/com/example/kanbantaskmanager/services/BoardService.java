package com.example.kanbantaskmanager.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.dtos.BoardDto;
import com.example.kanbantaskmanager.dtos.CreateBoardDto;
import com.example.kanbantaskmanager.mappers.BoardMapper;
import com.example.kanbantaskmanager.models.Board;
import com.example.kanbantaskmanager.models.Status;
import com.example.kanbantaskmanager.models.Task;
import com.example.kanbantaskmanager.repositories.BoardRepository;
import com.example.kanbantaskmanager.repositories.StatusRepository;
import com.example.kanbantaskmanager.repositories.SubtaskRepository;
import com.example.kanbantaskmanager.repositories.TaskRepository;

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

    public BoardDto createBoard(CreateBoardDto boardDto) {
        Board newBoard = boardMapper.convertToEntity(boardDto);
        Board savedBoard = this.boardRepository.save(newBoard);

        if (boardDto.getStatusCodes() != null && !boardDto.getStatusCodes().isEmpty()) {
            List<String> defaultStatusColors = Arrays.asList("#49c3e5", "#8471f2", "#67e2ae", "#f083f0", "#e66465");
            AtomicInteger nextColorIndex = new AtomicInteger(0);

            List<Status> statusCodesToSave = boardDto.getStatusCodes().stream().map((name) -> {
                Status status = new Status(name);
                status.setBoard(savedBoard); // Set the board reference
                status.setColor(defaultStatusColors.get(nextColorIndex.getAndIncrement() % defaultStatusColors.size()));
                return status;
            }).collect(Collectors.toList());

            List<Status> savedStatusCodes = statusRepository.saveAll(statusCodesToSave);
            savedBoard.setStatus(savedStatusCodes);
        } else {
            savedBoard.setStatus(Collections.emptyList());
        }
        return boardMapper.convertToDto(savedBoard);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        // Delete subtasks associated with tasks belonging to the board
        List<Task> tasks = taskRepository.findByBoardId(boardId);
        for (Task task : tasks) {
            subtaskRepository.deleteByTaskId(task.getId());
        }

        // Delete tasks associated with the board
        taskRepository.deleteByBoardId(boardId);
        // Delete status codes associated with the board
        statusRepository.deleteByBoardId(boardId);
        // Delete the board itself
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