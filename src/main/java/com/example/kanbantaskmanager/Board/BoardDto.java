package com.example.kanbantaskmanager.Board;

public package com.example.kanbantaskmanager.Board;

import java.util.Set;


public class BoardDto {
    private Long id;
    private String name;

    protected BoardDto() {}

    public BoardDto(String name) {
        this.name = name;
    }

    @Override
public String toString() {
    return String.format("Board[id=%d, name='%s']", id, name);
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
