package com.example.kanbantaskmanager.Subtask;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubtaskService {
    
    @Autowired
    private SubtaskRepository subtaskRepository;

    public List<Subtask> findAll() {
        return this.subtaskRepository.findAll();
    }

    public Subtask getSubtaskById(Long id) {
        return subtaskRepository.findById(id).get();
    }

    public Subtask createSubtask(Subtask subtask) {
        return this.subtaskRepository.save(subtask);
    }

    public void deleteSubtask(Long id) {
        subtaskRepository.deleteById(id);
    }

    public void uptadeSubtask(Subtask subtask, Long id) {
        subtaskRepository.save(subtask);
    }

}
