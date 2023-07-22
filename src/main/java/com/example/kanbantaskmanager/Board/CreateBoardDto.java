package com.example.kanbantaskmanager.Board;

import java.util.Set;


public class CreateBoardDto {
    private Long id;
    private String name;
    private Set<Long> columns;

    protected CreateBoardDto() {}

    public CreateBoardDto(String name) {
        this.name = name;
    }

    @Override
public String toString() {
    return String.format("Board[id=%d, name='%s']", id, name);
 }

 public Long getId() {
        return id;
    }
    
    public void SetId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getColumnIds() {
        return this.columns;
    }

    public void setColumnsIds(Set<Long> columnIds) {
        this.columns = columnIds;
    }
    

}
