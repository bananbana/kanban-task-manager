package com.example.kanbantaskmanager.Column;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ColumnController {
    
    @Autowired
    private ColumnService columnService;

    @GetMapping("/columns")
    public List<Column> getAll() {
        return this.columnService.findAll();
    }

    @GetMapping("/column/{id}")
    public Column getOne(@PathVariable("id") Long id) {
        return columnService.getColumnById(id);
    }

    @PostMapping("/columns")
    public Column create(@RequestBody Column column) {
        return this.columnService.createColumn(column);
    }

    @DeleteMapping("/columns/{id}")
    public void removeColumn(@PathVariable("id") Long id) {
        columnService.deleteColumn(id);
    }

    @PutMapping("/columns/{id}")
    public Column uptade(@RequestBody Column column, @PathVariable Long id) {
        columnService.uptadeColumn(column, id);
        return column;
    }
}
