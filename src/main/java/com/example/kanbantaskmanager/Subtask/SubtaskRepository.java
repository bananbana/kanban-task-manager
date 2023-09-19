package com.example.kanbantaskmanager.Subtask;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubtaskRepository extends JpaRepository <Subtask, Long> {
    List<Subtask> findByTaskId(Long taskId);
    @Modifying
    @Query("Delete From Subtask st Where st.task.id IN (SELECT t.id FROM Task t WHERE t.board.id = :boardId)")
    void deleteByBoardId(@Param("boardId") Long boardId);

    @Modifying
    @Query("DELETE FROM Subtask st WHERE st.task.id = :taskId")
    void deleteByTaskId(@Param("taskId") Long taskId);
}
