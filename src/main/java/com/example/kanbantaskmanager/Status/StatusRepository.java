
package com.example.kanbantaskmanager.Status;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatusRepository extends JpaRepository <Status, Long> {
    List<Status> findAllByBoardId(Long id);
    @Modifying
    @Query("DELETE FROM Status s WHERE s.board.id = :boardId")
        void deleteByBoardId(@Param("boardId") Long boardId);

}