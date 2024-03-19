package com.example.kanbantaskmanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kanbantaskmanager.dtos.CreateSubtaskDto;
import com.example.kanbantaskmanager.mappers.SubtaskMapper;
import com.example.kanbantaskmanager.models.Subtask;
import com.example.kanbantaskmanager.services.SubtaskService;

@RestController
@RequestMapping("user/tasks")
public class TaskController {

    @Autowired
    private SubtaskService subtaskService;
    @Autowired
    private SubtaskMapper subtaskMapper;

    @GetMapping("/{taskId}/subtasks/{subtaskId}")
    public CreateSubtaskDto getOneSubtask(@PathVariable Long taskId, @PathVariable Long subtaskId) {
        Subtask searchedSubtask = subtaskService.getSubtaskById(subtaskId);
        return subtaskMapper.convertToDto(searchedSubtask);
    }

    @GetMapping("/{taskId}/subtasks")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<CreateSubtaskDto> getSubtasksByTask(@PathVariable Long taskId) {
        List<CreateSubtaskDto> subtasksByTask = subtaskService.getSubtasksByTaskId(taskId);
        return subtasksByTask;
    }

    @PostMapping("/{taskId}/subtasks")
    public CreateSubtaskDto addSubtaskToTask(@PathVariable Long taskId, @RequestBody CreateSubtaskDto subtaskDto) {
        return this.subtaskService.createSubtask(subtaskDto, taskId);
    }

    @DeleteMapping("/{taskId}/subtasks/{subtaskId}")
    void removeSubtaskFromTask(@PathVariable Long taskId, @PathVariable Long subtaskId) {
        subtaskService.deleteSubtask(subtaskId);
    }

    @PutMapping("/{taskId}/subtasks/{subtaskId}")
    public CreateSubtaskDto updateSubtask(@PathVariable Long taskId, @RequestBody CreateSubtaskDto subtaskDto,
            @PathVariable Long subtaskId) {
        subtaskService.updateSubtask(taskId, subtaskDto, subtaskId);
        return subtaskDto;
    }
}
