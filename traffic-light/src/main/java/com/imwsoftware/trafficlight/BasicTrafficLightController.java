package com.imwsoftware.trafficlight;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BasicTrafficLightController implements TrafficLightController {

    private LightState currentState = LightState.RED;

    @Value("${light.duration.green:10000}")
    private long greenDuration;

    @Value("${light.duration.yellow:3000}")
    private long yellowDuration;

    @Value("${light.duration.red:10000}")
    private long redDuration;

    @Override
    public void nextState() {
        switch (currentState) {
            case RED -> currentState = LightState.GREEN;
            case GREEN -> currentState = LightState.YELLOW;
            case YELLOW -> currentState = LightState.RED;
        }
    }

    @Override
    public LightState getCurrentState() {
        return currentState;
    }

    @Override
    public long getCurrentDuration() {
        return switch (currentState) {
            case GREEN -> greenDuration;
            case YELLOW -> yellowDuration;
            case RED -> redDuration;
        };
    }

    @Override
    public void reset() {
        currentState = LightState.RED;
    }
}