package com.example.kanbantaskmanager.Column;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColumnService {
    
    @Autowired
    private ColumnRepository columnRepository;

    public List<Column> findAll() {
        return this.columnRepository.findAll();
    }

    public Column getColumnById(Long id) {
        return columnRepository.findById(id).get();
    }

    public Column createColumn(Column column) {
        return this.columnRepository.save(column);
    }

    public void deleteColumn(Long id) {
        columnRepository.deleteById(id);
    }

    public void uptadeColumn (Column column, Long id) {
        columnRepository.save(column);
    }
}
