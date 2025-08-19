package com.imwsoftware.microservice.controller;

import com.imwsoftware.microservice.service.LaunchThreadsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/threads")
public class LaunchThreadsController {

    private final LaunchThreadsService launchThreadsService;

    public LaunchThreadsController(LaunchThreadsService launchThreadsService) {
        this.launchThreadsService = launchThreadsService;
    }

    @RequestMapping("/launch")
    @ResponseStatus(org.springframework.http.HttpStatus.ACCEPTED)
    public ResponseEntity<Object> launchThreads() {
        launchThreadsService.launchThreads();
        return ResponseEntity.accepted().build();
    }

    @RequestMapping("/launch-executor")
    @ResponseStatus(org.springframework.http.HttpStatus.ACCEPTED)
    public ResponseEntity<Object> launchExecutorThreads() {
        launchThreadsService.launchExecutorThreads();
        return ResponseEntity.accepted().build();
    }
}
