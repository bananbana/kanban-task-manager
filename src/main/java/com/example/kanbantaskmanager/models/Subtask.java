package com.example.kanbantaskmanager.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SUBTASK")
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private Boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Subtask(String title, Boolean isCompleted) {
        this.title = title;
        this.isCompleted = isCompleted;
    }

    public Subtask() {
    }

    @Override
    public String toString() {
        return String.format("Subtask[id=%d, title='%s', isCompleted=%b]", id, title, isCompleted);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
