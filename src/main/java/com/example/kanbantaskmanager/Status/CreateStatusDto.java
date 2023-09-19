package com.example.kanbantaskmanager.Status;

public class CreateStatusDto {

    private Long id;
    private String name;
    private Long boardId;

    public CreateStatusDto (String name) {
    this.name = name;
    }

    protected CreateStatusDto () {}

    @Override
    public String toString() {
        return String.format("Status[name='%s']", name);
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

    public Long getBoardId() {
        return this.boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

}
