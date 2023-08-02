package com.example.kanbantaskmanager.Status;

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
public class StatusController {
    
    @Autowired
    private StatusService statusService;

    @GetMapping("/status_codes")
    public List<Status> getAll() {
        return this.statusService.findAll();
    }

    @GetMapping("/status_codes/{id}")
    public Status getOne(@PathVariable("id") Long id) {
        return statusService.getStatusById(id);
    }

    @PostMapping("/status_codes")
    public Status create(@RequestBody Status status) {
        return this.statusService.createStatus(status);
    }

    @DeleteMapping("/status_codes/{id}")
    public void removeStatus(@PathVariable("id") Long id) {
        statusService.deleteStatus(id);
    }

    @PutMapping("/status_codes/{id}")
    public Status uptade(@RequestBody Status status, @PathVariable Long id) {
        statusService.uptadeStatus(status, id);
        return status;
    }
}
