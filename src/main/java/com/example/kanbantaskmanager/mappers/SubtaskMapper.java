package com.example.kanbantaskmanager.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.dtos.CreateSubtaskDto;
import com.example.kanbantaskmanager.models.Subtask;
import com.example.kanbantaskmanager.models.Task;
import com.example.kanbantaskmanager.services.TaskService;

@Service
public class SubtaskMapper {

    @Autowired
    private TaskService taskService;

    public CreateSubtaskDto convertToDto(Subtask searchedSubtask) {
        CreateSubtaskDto newSubtask = new CreateSubtaskDto();
        newSubtask.setId(searchedSubtask.getId());
        newSubtask.setTitle(searchedSubtask.getTitle());
        newSubtask.setIsCompleted(searchedSubtask.getIsCompleted());
        newSubtask.setTaskId(searchedSubtask.getTask().getId());

        return newSubtask;
    }

    public Subtask convertToEntity(CreateSubtaskDto subtaskDto) {
        Subtask newSubtask = new Subtask();
        Long taskId = subtaskDto.getTaskId();
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID must not be null");
        }

        Task task = taskService.getTaskById(taskId);
        newSubtask.setId(subtaskDto.getId());
        newSubtask.setTitle(subtaskDto.getTitle());
        newSubtask.setIsCompleted(subtaskDto.getIsCompleted());
        newSubtask.setTask(task);

        return newSubtask;
    }
}
