package com.example.kanbantaskmanager.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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
import com.example.kanbantaskmanager.models.User;
import com.example.kanbantaskmanager.repositories.BoardRepository;
import com.example.kanbantaskmanager.repositories.StatusRepository;
import com.example.kanbantaskmanager.repositories.SubtaskRepository;
import com.example.kanbantaskmanager.repositories.TaskRepository;
import com.example.kanbantaskmanager.repositories.UserRepository;

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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public void assignBoardToUser(Long userId, Long ownerId, Long boardId) {
        User user = userService.getUserById(userId);

        Set<Board> newBoards = new HashSet<>(user.getBoards());

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("No board with provided id found"));
        User boardOwner = board.getOwner();
        if (boardOwner.getId() == ownerId) {
            newBoards.add(board);

            user.setBoards(newBoards);
            userRepository.save(user);
        } else {
            System.out.println("You are not an owner of the board! You cannot share it with other users.");
        }
    }

    public void assignBoardAsAdmin(Long userId, Long boardId) {
        User user = userService.getUserById(userId);
        Set<Board> newBoards = new HashSet<>(user.getBoards());

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("No board with provided id found"));
        newBoards.add(board);
        user.setBoards(newBoards);
        userRepository.save(user);
    }

    public List<BoardDto> getBoardsForUser(Long userId) {
        User user = userService.getUserById(userId);

        Set<Board> boards = user.getBoards();
        List<BoardDto> boardDtos = boards.stream().map(boardMapper::convertToDto).toList();
        return boardDtos;
    }

    public BoardDto getBoardForUser(Long boardId, Long userId) {
        User user = userService.getUserById(userId);
        Board board = user.getBoards().stream().filter(b -> b.getId().equals(boardId)).findFirst()
                .orElseThrow(NoSuchElementException::new);

        return boardMapper.convertToDto(board);
    }

    public List<BoardDto> findAll() {
        List<Board> boardsList = this.boardRepository.findAll();
        List<BoardDto> boardDtos = boardsList.stream().map((board) -> boardMapper.convertToDto(board)).toList();
        return boardDtos;
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board with id " + boardId + " not found"));
    }

    public CreateBoardDto createBoard(CreateBoardDto boardDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Board newBoard = boardMapper.convertToCreateEntity(boardDto);
        newBoard.setOwner(user);
        newBoard.setUsers(Set.of(user));
        user.getBoards().add(newBoard);
        Board savedBoard = boardRepository.save(newBoard);

        if (boardDto.getStatusCodes() != null && !boardDto.getStatusCodes().isEmpty()) {
            List<String> defaultStatusColors = Arrays.asList("#49c3e5", "#8471f2", "#67e2ae", "#f083f0", "#e66465");
            AtomicInteger nextColorIndex = new AtomicInteger(0);

            List<Status> statusCodesToSave = boardDto.getStatusCodes().stream().map((name) -> {
                Status status = new Status();
                status.setName(name);
                status.setBoard(savedBoard); // Set the board reference
                status.setColor(defaultStatusColors.get(nextColorIndex.getAndIncrement() % defaultStatusColors.size()));
                return status;
            }).collect(Collectors.toList());

            List<Status> savedStatusCodes = statusRepository.saveAll(statusCodesToSave);
            savedBoard.setStatus(savedStatusCodes);
        } else {
            savedBoard.setStatus(Collections.emptyList());
        }
        return boardMapper.convertToCreateBoardDto(savedBoard);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board with provided ID does not exist."));
        if (board != null) {
            for (User user : board.getUsers()) {
                user.getBoards().remove(board);
            }
        }

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

    public BoardDto assignOwner(Long boardId, Long userId) {
        Board boardToOwn = this.getBoardById(boardId);
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No user with such id."));

        boardToOwn.setOwner(owner);
        boardRepository.save(boardToOwn);

        return boardMapper.convertToDto(boardToOwn);
    }

    public BoardDto updateUserAccess(List<Long> userIds, Long boardId) {
        Board boardToUpdate = this.getBoardById(boardId);

        if (!userIds.contains(boardToUpdate.getOwner().getId())) {
            userIds.add(boardToUpdate.getOwner().getId());
        }
        Set<User> updatedUsers = userRepository.findAllById(userIds).stream().collect(Collectors.toSet());
        Set<User> usersToRemoveAccess = boardToUpdate.getUsers();

        usersToRemoveAccess.removeAll(updatedUsers);
        for (User u : usersToRemoveAccess) {
            Set<Board> newBoards = u.getBoards();
            Board boardToRemoveAccessTo = this.getBoardById(boardId);
            newBoards.remove(boardToRemoveAccessTo);
            u.setBoards(newBoards);
            userRepository.save(u);
        }
        for (User newU : updatedUsers) {
            newU.getBoards().add(boardToUpdate);
            userRepository.save(newU);
        }
        boardToUpdate.setUsers(updatedUsers);
        Board savedBoard = boardRepository.save(boardToUpdate);
        return boardMapper.convertToDto(savedBoard);
    }

    public BoardDto removeUserFromYourBoard(Long userId, Long boardId) {
        Board boardToUpdate = this.getBoardById(boardId);

        // set from which i remove a user:
        Set<User> boardUsers = boardToUpdate.getUsers();
        // check if the user i want to remove is not an owner
        if (boardToUpdate.getOwner().getId() != userId) {
            // get user entity from repository
            User userToRemove = userService.getUserById(userId);
            // get the users boards
            Set<Board> userBoards = userToRemove.getBoards();
            // remove board i don't want the user to have access to from hist boards set
            userBoards.remove(boardToUpdate);
            // save updated boards set
            userToRemove.setBoards(userBoards);
            // save the user with updated boards set
            userRepository.save(userToRemove);
            // remove the user from boards users set
            boardUsers.remove(userToRemove);
            // save new users set
            boardToUpdate.setUsers(boardUsers);
            // save board with updated users set to repository
            boardRepository.save(boardToUpdate);
        }

        return boardMapper.convertToDto(boardToUpdate);
    }
}