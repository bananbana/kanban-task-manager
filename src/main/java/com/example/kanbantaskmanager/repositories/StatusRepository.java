
package com.example.kanbantaskmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.kanbantaskmanager.models.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findAllByBoardId(Long id);

    @Modifying
    @Query("DELETE FROM Status s WHERE s.board.id = :boardId")
        void deleteByBoardId(@Param("boardId") Long boardId);

        Long countByBoardId(Long boardId);
}

