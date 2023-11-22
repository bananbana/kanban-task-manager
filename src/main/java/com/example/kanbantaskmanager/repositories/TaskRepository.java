package com.example.kanbantaskmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.kanbantaskmanager.models.Task;

public interface TaskRepository extends JpaRepository <Task, Long> {
    List<Task> findByBoardId(Long boardId);
    List<Task> findByStatusId(Long statusId);
    @Modifying
    @Query("DELETE FROM Task t WHERE t.board.id = :boardId")
    void deleteByBoardId(@Param("boardId") Long boardId);
}
