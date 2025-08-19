package com.imwsoftware.trafficlight;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TrafficLightRestController {

    private final TrafficLightController controller;

    public TrafficLightRestController(TrafficLightController controller) {
        this.controller = controller;
    }

    // GET /light → returns the current state
    @GetMapping("/light")
    public Map<String, String> getCurrentState() {
        return Map.of(
            "state", controller.getCurrentState().name(),
            "durationMillis", String.valueOf(controller.getCurrentDuration())
        );
    }

    // POST /light/next → manually trigger next state
    @PostMapping("/light/next")
    public Map<String, String> nextState() {
        controller.nextState();
        return Map.of(
            "newState", controller.getCurrentState().name(),
            "durationMillis", String.valueOf(controller.getCurrentDuration())
        );
    }
}