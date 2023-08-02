package com.example.kanbantaskmanager.Task;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;

    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    public CreateTaskDto createTask(CreateTaskDto taskDto) {
        Task newTask = taskMapper.convertToEntity(taskDto);
        Task savedTask = this.taskRepository.save(newTask);
        return taskMapper.convertToDto(savedTask);
    }

    public Set<Task> findAllById(Set<Long> ids) {
        return new HashSet<Task>(this.taskRepository.findAllById(ids));
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void updateTaks(Task task, Long id) {
        taskRepository.save(task);
    }
    
}
