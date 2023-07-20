package com.example.kanbantaskmanager.Column;

import java.util.List;

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
@Table(name="COLUMN")
public class Column {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;

@ManyToOne
@JoinColumn(name="board_id")
private Board board;

@OneToMany
@JoinColumn(name="task_id")
private List<Task> tasks;

    public Column (String name) {
        this.name = name;
    }

    protected Column () {}

    @Override
    public String toString() {
        return String.format("Column[name='%s']", name);
    }

    public Long getId() {
        return id;
    }
    
    public void SetId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
