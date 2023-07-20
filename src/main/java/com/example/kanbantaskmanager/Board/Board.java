package com.example.kanbantaskmanager.Board;

import com.example.kanbantaskmanager.Column.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="BOARD")
public class Board {
        
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
private String name;

@ManyToOne
@JoinColumn(name="column_id")
private Column column;

public Board(String name) {
    this.name = name;
}

protected Board() {}

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

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
    
}
