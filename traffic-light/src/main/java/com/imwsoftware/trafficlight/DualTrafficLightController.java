package com.imwsoftware.trafficlight;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DualTrafficLightController {

    private LightState nsState = LightState.RED;

    @Value("${light.duration.green:10000}")
    private long greenDuration;

    @Value("${light.duration.yellow:3000}")
    private long yellowDuration;

    @Value("${light.duration.red:10000}")
    private long redDuration;

    public void nextState() {
        switch (nsState) {
            case RED -> nsState = LightState.GREEN;
            case GREEN -> nsState = LightState.YELLOW;
            case YELLOW -> nsState = LightState.RED;
        }
    }

    public LightState getNorthSouth() {
        return nsState;
    }

    public LightState getEastWest() {
        return switch (nsState) {
            case GREEN -> LightState.RED;
            case YELLOW -> LightState.RED;
            case RED -> LightState.GREEN;
        };
    }

    public long getCurrentDuration() {
        return switch (nsState) {
            case GREEN -> greenDuration;
            case YELLOW -> yellowDuration;
            case RED -> redDuration;
        };
    }

    public void reset() {
        nsState = LightState.RED;
    }
}
