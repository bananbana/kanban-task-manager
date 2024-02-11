package com.example.kanbantaskmanager.dtos;

import java.util.List;

public class BoardDto {
    private Long id;
    private String name;
    private List<Long> statusCodes;
    private List<Long> tasks;
    private List<Long> userIds;
    private Long ownerId;

    public BoardDto(String name) {
        this.name = name;
    }

    public BoardDto() {
    }

    @Override
    public String toString() {
        return "Board[name='%s']".formatted(name);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getStatusCodes() {
        return this.statusCodes;
    }

    public void setStatusCodes(List<Long> statusCodes) {
        this.statusCodes = statusCodes;
    }

    public List<Long> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Long> tasks) {
        this.tasks = tasks;
    }

    public List<Long> getUserIds() {
        return this.userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
