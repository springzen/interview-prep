package com.imwsoftware.trafficlight;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/light")
public class DualTrafficLightRestController {

    private final DualTrafficLightController controller;
    private final TrafficLightScheduler scheduler;

    public DualTrafficLightRestController(DualTrafficLightController controller, TrafficLightScheduler scheduler) {
        this.controller = controller;
        this.scheduler = scheduler;
    }

    @GetMapping("/north-south")
    public Map<String, String> getNS() {
        return Map.of(
                "state", controller.getNorthSouth().name(),
                "durationMillis", String.valueOf(controller.getCurrentDuration()),
                "millisRemaining", String.valueOf(scheduler.getMillisRemaining())
        );
    }

    @GetMapping("/east-west")
    public Map<String, String> getEW() {
        return Map.of(
                "state", controller.getEastWest().name(),
                "durationMillis", String.valueOf(controller.getCurrentDuration()),
                "millisRemaining", String.valueOf(scheduler.getMillisRemaining())
        );
    }

    @PostMapping("/reset")
    public Map<String, String> reset() {
        scheduler.reset();
        return Map.of("message", "Reset both directions to RED");
    }
}