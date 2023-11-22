package com.example.kanbantaskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kanbantaskmanager.models.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
