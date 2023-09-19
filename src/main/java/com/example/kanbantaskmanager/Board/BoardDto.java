package com.example.kanbantaskmanager.Board;

public class BoardDto {
    private Long id;
    private String name;

    public BoardDto (String name) {
        this.name = name;
    }

    protected BoardDto() {}

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
}
