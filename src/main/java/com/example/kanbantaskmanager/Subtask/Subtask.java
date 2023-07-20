package com.example.kanbantaskmanager.Subtask;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="SUBTASK")
public class Subtask {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    private Boolean isCompleated;

    public Subtask(String title, Boolean isCompleated) {
        this.title = title;
        this.isCompleated = isCompleated;
    }

    protected Subtask () {}

    @Override
    public  String toString() {
        return String.format("Subtask[id=%d, title='%s', isCompleated=%b]", id, title, isCompleated);
    }

    public Long getId() {
        return id;
    }
    
    public void SetId(long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsCompleated() {
        return isCompleated;
    }

    public void setIsCompleated(Boolean isCompleated) {
        this.isCompleated = isCompleated;
    }
    
}
