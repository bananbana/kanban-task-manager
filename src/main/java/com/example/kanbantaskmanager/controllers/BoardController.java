package com.example.kanbantaskmanager.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.kanbantaskmanager.dtos.BoardDto;
import com.example.kanbantaskmanager.dtos.ShareBoardDto;
import com.example.kanbantaskmanager.dtos.TaskDto;
import com.example.kanbantaskmanager.services.BoardService;
import com.example.kanbantaskmanager.services.TaskService;

@RestController
@RequestMapping("boards")
@CrossOrigin(origins = "http://localhost:5173")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private TaskService taskService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        List<BoardDto> allBoards = this.boardService.findAll();
        return ResponseEntity.ok(allBoards);
    }

    @PutMapping("/{boardId}/owner_assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BoardDto> assignBoardOwnership(@RequestBody BoardDto boardDto, @RequestParam Long boardId) {
        BoardDto boardToUpdate = boardService.assignOwner(boardId, boardDto.getOwnerId());
        return ResponseEntity.ok(boardToUpdate);
    }

    @GetMapping("/tasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/assign/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignBoardToUser(@PathVariable Long userId,
            @RequestBody ShareBoardDto boardDto) {
        Long boardId = boardDto.getBoardId();
        boardService.assignBoardAsAdmin(userId, boardId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBoardAsAdmin(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
    }

    @PutMapping("/{boardId}/update_access")
    @PreAuthorize("hasRole ('ADMIN')")
    public BoardDto updateAccessToBoard(@RequestBody List<Long> userIds, @PathVariable Long boardId) {
        return boardService.updateUserAccess(userIds, boardId);
    }
}
