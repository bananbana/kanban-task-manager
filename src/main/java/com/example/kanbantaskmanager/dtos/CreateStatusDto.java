package com.example.kanbantaskmanager.dtos;

public class CreateStatusDto {

    private Long id;
    private String name;
    private Long boardId;
    private String color;

    public CreateStatusDto(String name) {
        this.name = name;
    }

    public CreateStatusDto() {
    }

    @Override
    public String toString() {
        return String.format("Status[name='%s', color='%s']", name, color);
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

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getBoardId() {
        return this.boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

}
