package com.example.kanbantaskmanager.Status;

import java.util.Set;

import com.example.kanbantaskmanager.Board.Board;
import com.example.kanbantaskmanager.Task.Task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="STATUS")
public class Status {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;

@ManyToOne
@JoinColumn(name="board_id")
private Board board;

@OneToMany(mappedBy = "status")
private Set<Task> tasks;

    public Status (String name) {
        this.name = name;
    }

    protected Status () {}

    @Override
    public String toString() {
        return String.format("Status[name='%s']", name);
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
