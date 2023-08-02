package com.example.kanbantaskmanager.Board;

import java.util.Set;

import com.example.kanbantaskmanager.Status.Status;
import com.example.kanbantaskmanager.Task.Task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="BOARD")
public class Board {
        
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
private String name;

@OneToMany(mappedBy = "board")
private Set<Status> statusCodes;

@OneToMany(mappedBy = "board")
private Set<Task> tasks;

public Board(String name) {
    this.name = name;
}

protected Board() {}

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

    public Set<Status> getStatus() {
        return statusCodes;
    }

    public void setStatus(Set<Status> statusCodes) {
        this.statusCodes = statusCodes;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
    
}
