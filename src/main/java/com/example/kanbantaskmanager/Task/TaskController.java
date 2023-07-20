package com.example.kanbantaskmanager.Task;

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
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> getAll() {
        return this.taskService.findAll();
    }

    @GetMapping("/tasks/{id}")
    public Task getOne(@PathVariable("id") Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/tasks")
        public Task create(@RequestBody Task task) {
            return this.taskService.createTask(task);
        }
     
    @DeleteMapping("/tasks/{id}")
        public void removeTask(@PathVariable("id") Long id) {
            taskService.deleteTask(id);
        }

    @PutMapping("/tasks/{id}")
    public Task uptade(@RequestBody Task task, @PathVariable Long id) {
        taskService.updateTaks(task, id);
        return task;
    }
}
