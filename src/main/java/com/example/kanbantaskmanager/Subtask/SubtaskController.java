package com.example.kanbantaskmanager.Subtask;

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
public class SubtaskController {
    
    @Autowired
    private SubtaskService subtaskService;

    @GetMapping("/subtasks")
    public List<Subtask> getAll() {
        return this.subtaskService.findAll();
    }

    @GetMapping ("subtasks/{id}")
    public Subtask getOne(@PathVariable("id") Long id) {
        return subtaskService.getSubtaskById(id);
    }

    @PostMapping("/subtasks")
    public Subtask create(@RequestBody Subtask subtask) {
        return this.subtaskService.createSubtask(subtask);
    }

    @DeleteMapping("/subtasks/{id}")
    public void removeSubtask(@PathVariable("id") Long id) {
        subtaskService.deleteSubtask(id);
    }

    @PutMapping("/subtasks/{id}")
    public Subtask uptade(@RequestBody Subtask subtask, @PathVariable Long id) {
        subtaskService.uptadeSubtask(subtask, id);
        return subtask;
    }
}
