package com.example.kanbantaskmanager.Task;

import java.util.List;


public class CreateTaskDto {
    private Long id;
    private String title;
    private String description;
    private Long statusId;
    private List<Long> subtasks;
    private Long boardId;

    protected CreateTaskDto() {}

    public CreateTaskDto(String title, String description, Long id) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Task[id=%d, title='%s', description='%s']", id, title, description);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStatusId() {
        return this.statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public List<Long> getSubtasks() {
        return this.subtasks;
    }

    public void setSubtasks(List<Long> subtasks) {
        this.subtasks = subtasks;
    }

    public Long getBoardId() {
        return this.boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
}
