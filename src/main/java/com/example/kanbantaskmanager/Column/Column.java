package com.example.kanbantaskmanager.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="COLUMN")
public class Column {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;


    public Column (String name) {
        this.name = name;
    }

    protected Column () {}

    @Override
    public String toString() {
        return String.format("Column[name='%s']", name);
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
}
