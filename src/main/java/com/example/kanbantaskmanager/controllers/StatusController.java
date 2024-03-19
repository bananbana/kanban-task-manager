package com.example.kanbantaskmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.kanbantaskmanager.models.Status;
import com.example.kanbantaskmanager.services.StatusService;

@RestController
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping("/status_codes/{id}")
    public Status getOne(@PathVariable Long id) {
        return statusService.getStatusById(id);
    }
}
