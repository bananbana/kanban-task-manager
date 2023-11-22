package com.example.kanbantaskmanager.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "board")
    private List<Status> statusCodes;

    @OneToMany(mappedBy = "board")
    private List<Task> tasks;

    public Board(String name) {
        this.name = name;
    }

    public Board() {
    }

    @Override
    public String toString() {
        return String.format("Board[id=%d, name='%s']", id, name);
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

    public List<Status> getStatus() {
        return statusCodes;
    }

    public void setStatus(List<Status> statusCodes) {
        this.statusCodes = statusCodes;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
