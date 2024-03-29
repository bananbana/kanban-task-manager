package com.example.kanbantaskmanager.dtos;

public class CreateSubtaskDto {
    private Long id;
    private String title;
    private Boolean isCompleted;
    private Long taskId;

    public CreateSubtaskDto() {
    }

    public CreateSubtaskDto(Long id, String title, Boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return "Subtask[id=%d, title='%s', isCompleted=%b]".formatted(id, title, isCompleted);
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Boolean getIsCompleted() {
        return this.isCompleted;
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
