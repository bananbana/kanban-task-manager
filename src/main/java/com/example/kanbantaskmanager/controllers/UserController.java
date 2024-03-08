package com.example.kanbantaskmanager.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kanbantaskmanager.dtos.AssignRoleDto;
import com.example.kanbantaskmanager.dtos.BoardDto;
import com.example.kanbantaskmanager.dtos.ColorUpdateDto;
import com.example.kanbantaskmanager.dtos.CreateBoardDto;
import com.example.kanbantaskmanager.dtos.CreateStatusDto;
import com.example.kanbantaskmanager.dtos.CreateTaskDto;
import com.example.kanbantaskmanager.dtos.ShareBoardDto;
import com.example.kanbantaskmanager.dtos.TaskDto;
import com.example.kanbantaskmanager.dtos.UpdateUserDto;
import com.example.kanbantaskmanager.dtos.UpdatePasswordDto;
import com.example.kanbantaskmanager.dtos.UserDto;
import com.example.kanbantaskmanager.mappers.TaskMapper;
import com.example.kanbantaskmanager.mappers.UserMapper;
import com.example.kanbantaskmanager.models.Board;
import com.example.kanbantaskmanager.models.Task;
import com.example.kanbantaskmanager.models.User;
import com.example.kanbantaskmanager.security.services.UserDetailsImpl;
import com.example.kanbantaskmanager.services.BoardService;
import com.example.kanbantaskmanager.services.StatusService;
import com.example.kanbantaskmanager.services.TaskService;
import com.example.kanbantaskmanager.services.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("user_details")
    @PreAuthorize("hasRole('USER') or ('ADMIN')")
    public ResponseEntity<UserDto> getUserDetails(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getUserDetails(userDetails.getId()));
    }

    @GetMapping("all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @PutMapping("/{userId}/update")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UpdateUserDto updateUser(@PathVariable Long userId,
            @RequestBody UpdateUserDto userDto) {
        return userService.updateUserInformation(userId, userDto);
    }

    @PutMapping("/{userId}/password_change")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UpdateUserDto updateUserPassword(@PathVariable Long userId, @RequestBody UpdatePasswordDto userDto) {
        return userService.updateUserPassword(userId, userDto);
    }

    @GetMapping("/boards")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<BoardDto>> getBoardsForUser(Authentication authentication) {
        System.out.println(authentication.getDetails().toString());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<BoardDto> boards = boardService.getBoardsForUser(userDetails.getId());
        return ResponseEntity.ok(boards);
    }

    @DeleteMapping("/{userId}/delete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void deleteUserAccount(@PathVariable Long userId, @RequestBody UpdateUserDto userDto) {
        userService.deleteAccount(userDto, userId);
    }

    // Boards
    @GetMapping("/boards/{boardId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BoardDto> getBoardForUser(@PathVariable Long boardId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        BoardDto board = boardService.getBoardForUser(boardId, userDetails.getId());
        return ResponseEntity.ok(board);
    }

    @PutMapping("/boards/{boardId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BoardDto updateBoard(@RequestBody BoardDto boardDto, @PathVariable Long boardId) {
        boardService.updateBoard(boardDto, boardId);
        return boardDto;
    }

    @PutMapping("/boards/assign/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> assignBoardsToUser(@PathVariable Long userId,
            @RequestBody ShareBoardDto boardDto, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long boardId = boardDto.getBoardId();
        boardService.assignBoardToUser(userId, userDetails.getId(), boardId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/boards/{boardId}/user_access")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BoardDto removeUsersAccessToBoard(@PathVariable Long boardId, @RequestBody Map<String, Long> requestBody) {
        Long userId = requestBody.get("userId");
        return boardService.removeUserFromYourBoard(userId, boardId);
    }

    @PutMapping("/boards/{boardId}/abandon_access")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserDto removeYourAccessToSharedBoard(@PathVariable Long boardId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userService.abandonBoardAccess(boardId, userDetails.getId());
    }

    @DeleteMapping("/boards/{boardId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void removeBoard(@PathVariable Long boardId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User owner = userService.getUserById(userDetails.getId());
        Board boardToDelete = boardService.getBoardById(boardId);
        if (owner == boardToDelete.getOwner()) {
            boardService.deleteBoard(boardId);
        } else {
            System.err.println("You are not the owner of this board. You cannot delete it.");
        }
    }

    @PostMapping("/boards/create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CreateBoardDto> createBoard(@RequestBody CreateBoardDto boardDto,
            Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        CreateBoardDto createdBoard = boardService.createBoard(boardDto, userDetails.getId());

        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    // Tasks

    @GetMapping("/boards/{boardId}/tasks")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<TaskDto>> getTasksByBoard(@PathVariable Long boardId) {
        List<TaskDto> tasks = taskService.getTaskByBoardId(boardId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}/tasks/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public TaskDto getTaskFromBoard(@PathVariable Long boardId, @PathVariable Long taskId) {
        Task searchedTask = taskService.getTaskById(taskId);
        return taskMapper.convertToDto(searchedTask);
    }
    // add error handling when task with particular id exists, but not in provided
    // board...

    @PostMapping("/boards/{boardId}/tasks")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")

    public TaskDto createTask(@RequestBody CreateTaskDto taskDto, @PathVariable Long boardId) {
        return this.taskService.createTask(taskDto, boardId);
    }

    @PutMapping("/boards/{boardId}/tasks/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")

    public TaskDto updateTask(@PathVariable Long boardId, @RequestBody TaskDto taskDto, @PathVariable Long taskId) {
        taskService.updateTask(boardId, taskDto, taskId);
        return taskDto;
    }

    @PutMapping("/boards/{boardId}/tasks/{taskId}/new_status/{statusId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public TaskDto updateStatusOnDrag(@PathVariable Long boardId, @PathVariable Long taskId,
            @PathVariable Long statusId) {
        return taskService.updateStatusOnDrag(boardId, taskId, statusId);
    }

    @DeleteMapping("/boards/{boardId}/tasks/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void removeTaskFromBoard(@PathVariable Long boardId, @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    // Status Codes
    @GetMapping("/boards/{boardId}/status_codes")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CreateStatusDto>> getAllByBoardId(@PathVariable Long boardId) {
        List<CreateStatusDto> statusCodes = statusService.findAllByBoardId(boardId);
        return ResponseEntity.ok(statusCodes);
    }

    @PostMapping("/boards/{boardId}/status_codes")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")

    public CreateStatusDto create(@RequestBody CreateStatusDto statusDto, @PathVariable Long boardId) {
        return statusService.createStatus(statusDto, boardId);
    }

    @PutMapping("/boards/{boardId}/status_codes/{statusId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")

    public CreateStatusDto updateStatus(@PathVariable Long boardId, @RequestBody CreateStatusDto statusDto,
            @PathVariable Long statusId) {
        statusService.updateStatus(boardId, statusDto, statusId);
        return statusDto;
    }

    @DeleteMapping("/boards/{boardId}/status_codes/{statusId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void deleteStatus(@PathVariable Long boardId, @PathVariable Long statusId) {
        statusService.deleteStatus(statusId);
    }

    @PutMapping("/boards/{boardId}/status_codes/{statusId}/color")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public CreateStatusDto updateStatusColor(
            @PathVariable Long boardId,
            @PathVariable Long statusId,
            @RequestBody ColorUpdateDto colorUpdateDto) {
        String newColor = colorUpdateDto.getNewColor();
        return statusService.updateStatusColor(boardId, statusId, newColor);
    }

    // Admin stuff
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole ('ADMIN')")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole ('ADMIN')")
    public UserDto updateUserRoles(@PathVariable Long userId, @RequestBody AssignRoleDto roleDto) {
        return userService.addUserRoles(userId, roleDto.getRoleId());
    }

    @PutMapping("/{userId}/remove_admin")
    @PreAuthorize("hasRole ('ADMIN')")
    public UserDto removeAdminRole(@PathVariable Long userId) {
        return userService.removeAdminRole(userId);
    }

    @PutMapping("/{userId}/share_board/{boardId}")
    @PreAuthorize("hasRole ('ADMIN')")
    public ResponseEntity<?> adminBoardShare(@PathVariable Long userId, @PathVariable Long boardId) {
        boardService.assignBoardAsAdmin(userId, boardId);
        return ResponseEntity.ok().build();
    }
}
