package com.example.kanbantaskmanager.Task;

import java.util.Set;

import com.example.kanbantaskmanager.Board.Board;
import com.example.kanbantaskmanager.Status.Status;
import com.example.kanbantaskmanager.Subtask.Subtask;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="TASK")
public class Task {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name="status_id")
    private Status status;

    @OneToMany(mappedBy = "task")
    private Set<Subtask> subtasks;

    public Task(String title, String description, String status) {
        this.title = title;
        this.description = description;
    }

    protected Task() {
    }

    @Override
    public String toString() {
        return String.format("Task[id=%d, title='%s', description='%s']", id, title, description);
    }

    public Long getId() {
        return id;
    }
    
    public void SetId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Set<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Set<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
