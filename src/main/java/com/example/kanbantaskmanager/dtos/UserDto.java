package com.example.kanbantaskmanager.dtos;

import java.util.List;

public class UserDto {
    private Long id;
    private String username;
    private String email;
    private List<Long> roleIds;
    private List<Long> boardIds;
    // private List<Long> ownedBoardsIds;

    public UserDto(String username) {
        this.username = username;
    }

    public UserDto() {
    }

    @Override
    public String toString() {
        return "User [username='%s']".formatted(username);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getRoles() {
        return this.roleIds;
    }

    public void setRoles(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Long> getBoards() {
        return this.boardIds;
    }

    public void setBoards(List<Long> boardIds) {
        this.boardIds = boardIds;
    }

    // public List<Long> getOwnedBoardsIds() {
    // return this.ownedBoardsIds;
    // }

    // public void setOwnedBoardsIds(List<Long> ownedBoardsIds) {
    // this.ownedBoardsIds = ownedBoardsIds;
    // }
}
