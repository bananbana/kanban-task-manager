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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kanbantaskmanager.Subtask.CreateSubtaskDto;
import com.example.kanbantaskmanager.Subtask.Subtask;
import com.example.kanbantaskmanager.Subtask.SubtaskMapper;
import com.example.kanbantaskmanager.Subtask.SubtaskService;

@RestController
@RequestMapping("tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    @Autowired
    private SubtaskService subtaskService;
    @Autowired
    private SubtaskMapper subtaskMapper;

    @GetMapping ("/{taskId}/subtasks/{subtaskId}")
    public CreateSubtaskDto getOneSubtask(@PathVariable Long taskId, @PathVariable Long subtaskId) {
        Subtask searchedSubtask = subtaskService.getSubtaskById(subtaskId);
        return subtaskMapper.convertToDto(searchedSubtask);
    }

    @GetMapping("/{taskId}/subtasks")
    public List<CreateSubtaskDto> getSubtasksByTask(@PathVariable Long taskId) {
        List<CreateSubtaskDto> subtasksByTask = subtaskService.getSubtasksByTaskId(taskId);
        return subtasksByTask;
    }

    @PostMapping("/{taskId}/subtasks")
        public CreateSubtaskDto addSubtaskToTask(@PathVariable Long taskId, @RequestBody CreateSubtaskDto subtaskDto) {
           return this.subtaskService.createSubtask((subtaskDto));
    }

    @DeleteMapping("/{taskId}/subtasks/{subtaskId}")
    public void removeSubtaskFromTask(@PathVariable Long taskId, @PathVariable Long subtaskId) {
        subtaskService.deleteSubtask(subtaskId);
    }

    @PutMapping("/{taskId}/subtasks/{subtaskId}")
    public CreateSubtaskDto updateSubtask(@PathVariable Long taskId, @RequestBody CreateSubtaskDto subtaskDto, @PathVariable Long subtaskId) {
        subtaskService.updateSubtask(taskId, subtaskDto, subtaskId);
        return subtaskDto;
    }
}
