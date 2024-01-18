package com.example.kanbantaskmanager.dtos;

import java.util.List;

//IMPORTANT don't delete this, u need this to create boards passing strings of status codes names instead of doing flips with ids

public class CreateBoardDto {
    private Long id;
    private String name;
    private List<String> statusCodes;
    private List<Long> userIds;
    private Long ownerId;

    public CreateBoardDto(String name) {
        this.name = name;
    }

    public CreateBoardDto() {
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

    public List<String> getStatusCodes() {
        return this.statusCodes;
    }

    public void setStatusCodes(List<String> statusCodes) {
        this.statusCodes = statusCodes;
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
