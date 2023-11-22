package com.example.kanbantaskmanager.dtos;

import java.util.List;

public class BoardDto {
    private Long id;
    private String name;
    private List<Long> statusCodes;

    public BoardDto(String name) {
        this.name = name;
    }

    public BoardDto() {
    }

    @Override
    public String toString() {
        return String.format("Board[name='%s']", name);
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
}
