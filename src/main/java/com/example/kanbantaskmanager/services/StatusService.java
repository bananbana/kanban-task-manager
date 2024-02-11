package com.example.kanbantaskmanager.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.dtos.CreateStatusDto;
import com.example.kanbantaskmanager.mappers.StatusMapper;
import com.example.kanbantaskmanager.models.Status;
import com.example.kanbantaskmanager.repositories.StatusRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private StatusMapper statusMapper;

    public List<CreateStatusDto> findAll() {
        List<Status> statusCodesList = this.statusRepository.findAll();
        List<CreateStatusDto> statusDtos = statusCodesList.stream().map((status) -> statusMapper.convertToDto(status))
                .toList();
        return statusDtos;
    }

    public List<CreateStatusDto> findAllByBoardId(Long boardId) {
        List<Status> statusList = statusRepository.findAllByBoardId(boardId);
        List<CreateStatusDto> statusDtos = statusList.stream().map((status) -> statusMapper.convertToDto(status))
                .toList();
        return statusDtos;

    }

    public Set<Status> findAllById(Set<Long> ids) {
        return new HashSet<Status>(this.statusRepository.findAllById(ids));
    }

    public Status getStatusById(Long id) {
        if (id == null) {
            throw new EntityNotFoundException("The given status id must not be null");
        }
        return statusRepository.findById(id).get();
    }

    public CreateStatusDto createStatus(CreateStatusDto statusDto, Long boardId) {
        statusDto.setBoardId(boardId);

        if (statusDto.getColor() == null) {
            List<String> defaultStatusColors = Arrays.asList("#49c3e5", "#8471f2", "#67e2ae", "#f083f0", "#e66465");
            AtomicInteger nextColorIndex = new AtomicInteger(statusRepository.countByBoardId(boardId).intValue());
            statusDto.setColor(defaultStatusColors.get(nextColorIndex.getAndIncrement() % defaultStatusColors.size()));

            // int nextColorIndex = statusRepository.countByBoardId(boardId).intValue() %
            // defaultStatusColors.size();
            // statusDto.setColor(defaultStatusColors.get(nextColorIndex));
        }

        Status status = statusMapper.convertToEntity(statusDto);
        Status newStatus = statusRepository.save(status);
        CreateStatusDto newStatusDto = statusMapper.convertToDto(newStatus);
        return newStatusDto;
    }

    @Transactional
    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }

    public CreateStatusDto updateStatus(Long boardId, CreateStatusDto statusDto, Long statusId) {
        Status statusToUpdate = this.getStatusById(statusId);
        if (statusToUpdate == null) {
            throw new EntityNotFoundException("Status with id " + statusId + " does not exist.");
        }

        statusToUpdate.setName(statusDto.getName());
        statusRepository.save(statusToUpdate);

        return statusMapper.convertToDto(statusToUpdate);
    }

    @Transactional
    public CreateStatusDto updateStatusColor(Long boardId, Long statusId, String color) {
        Status statusToUpdate = this.getStatusById(statusId);

        if (statusToUpdate == null) {
            throw new EntityNotFoundException("Status with id " + statusId + " does not exist");
        }

        statusToUpdate.setColor(color);
        statusRepository.save(statusToUpdate);

        return statusMapper.convertToDto(statusToUpdate);
    }
}
