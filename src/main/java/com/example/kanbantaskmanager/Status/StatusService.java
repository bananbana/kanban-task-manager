package com.example.kanbantaskmanager.Status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StatusService {
    
    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusMapper statusMapper;

    public List<CreateStatusDto> findAll() {
        List<Status> statusCodesList = this.statusRepository.findAll();
        List<CreateStatusDto> statusDtos = statusCodesList.stream().map((status) -> statusMapper.convertToDto(status)).toList();
        return statusDtos;
    }

    public List<CreateStatusDto> findAllByBoardId(Long boardId) {
        List<Status> statusList = statusRepository.findAllByBoardId(boardId);
        List<CreateStatusDto> statusDtos = statusList.stream().map((status) -> statusMapper.convertToDto(status)).toList();
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

    public CreateStatusDto createStatus(CreateStatusDto statusDto) {
        Status status = statusMapper.convertToEntity(statusDto);
        Status newStatus = statusRepository.save(status);
        CreateStatusDto newStatusDto = statusMapper.convertToDto(newStatus);
        return newStatusDto;
    }

    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }

    public void updateStatus (Status status, Long id) {
        statusRepository.save(status);
    }
}
