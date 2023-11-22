package com.example.kanbantaskmanager.dtos;

import java.util.List;

public class CreateBoardDto {
    private Long id;
    private String name;
    private List<String> statusCodes;

    public CreateBoardDto(String name) {
        this.name = name;
    }

    protected CreateBoardDto() {
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

    public List<String> getStatusCodes() {
        return this.statusCodes;
    }

    public void setStatusCodes(List<String> statusCodes) {
        this.statusCodes = statusCodes;
    }
}
