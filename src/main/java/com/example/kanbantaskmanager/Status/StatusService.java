package com.example.kanbantaskmanager.Status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    
    @Autowired
    private StatusRepository statusRepository;

    public List<Status> findAll() {
        return this.statusRepository.findAll();
    }

    public Set<Status> findAllById(Set<Long> ids) {
        return new HashSet<Status>(this.statusRepository.findAllById(ids));
    }

    public Status getStatusById(Long id) {
        return statusRepository.findById(id).get();
    }

    public Status createStatus(Status status) {
        return this.statusRepository.save(status);
    }

    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }

    public void uptadeStatus (Status status, Long id) {
        statusRepository.save(status);
    }
}
