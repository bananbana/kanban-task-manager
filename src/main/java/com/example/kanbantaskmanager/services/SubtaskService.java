
package com.example.kanbantaskmanager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.dtos.CreateSubtaskDto;
import com.example.kanbantaskmanager.mappers.SubtaskMapper;
import com.example.kanbantaskmanager.models.Subtask;
import com.example.kanbantaskmanager.models.Task;
import com.example.kanbantaskmanager.repositories.SubtaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SubtaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;
    @Autowired
    private SubtaskMapper subtaskMapper;
    @Autowired
    private TaskService taskService;

    public List<CreateSubtaskDto> findAll() {
        List<Subtask> subtaskList = this.subtaskRepository.findAll();
        List<CreateSubtaskDto> subtaskDtos = subtaskList.stream().map((subtask) -> subtaskMapper.convertToDto(subtask))
                .toList();

        return subtaskDtos;
    }

    public List<CreateSubtaskDto> getSubtasksByTaskId(Long taskId) {
        List<Subtask> subtasksByTaskId = subtaskRepository.findByTaskId(taskId);
        if (taskId == null) {
            throw new EntityNotFoundException("Provided task id must not be null");
        }

        List<CreateSubtaskDto> subtaskDtos = new ArrayList<>();
        for (Subtask subtask : subtasksByTaskId) {
            subtaskDtos.add(subtaskMapper.convertToDto(subtask));
        }
        return subtaskDtos;
    }

    public Subtask getSubtaskById(Long subtaskId) {
        return subtaskRepository.findById(subtaskId)
                .orElseThrow(() -> new EntityNotFoundException("Subtask with id " + subtaskId + " not found"));
    }

    public CreateSubtaskDto createSubtask(CreateSubtaskDto subtaskDto, Long taskId) {
        subtaskDto.setTaskId(taskId);
        subtaskDto.setIsCompleted(false);
        Subtask newSubtask = subtaskMapper.convertToEntity(subtaskDto);
        Subtask savedSubtask = this.subtaskRepository.save(newSubtask);
        return subtaskMapper.convertToDto(savedSubtask);
    }

    public void deleteSubtask(Long subtaskId) {
        Subtask subtask = getSubtaskById(subtaskId);
        subtaskRepository.delete(subtask);
    }

    public CreateSubtaskDto updateSubtask(Long taskId, CreateSubtaskDto subtaskDto, Long subtaskId) {
        Subtask subtaskToUpdate = this.getSubtaskById(subtaskId);
        if (subtaskToUpdate == null) {
            throw new EntityNotFoundException("Subtask with id " + subtaskId + " does not exist");
        }
        subtaskToUpdate.setTitle(subtaskDto.getTitle());
        if (subtaskDto.getIsCompleted() == null) {
            subtaskDto.setIsCompleted(false);
        }
        ;
        subtaskToUpdate.setIsCompleted(subtaskDto.getIsCompleted());
        subtaskToUpdate.setId(subtaskId);
        Task task = taskService.getTaskById(subtaskDto.getTaskId());
        subtaskToUpdate.setTask(task);

        subtaskRepository.save(subtaskToUpdate);
        return subtaskMapper.convertToDto(subtaskToUpdate);
    }
}
