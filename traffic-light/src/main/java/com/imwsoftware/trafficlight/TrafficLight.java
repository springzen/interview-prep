package com.imwsoftware.trafficlight;

public class TrafficLight {

    private LightState currentState = LightState.RED;

    private final long greenDuration;
    private final long yellowDuration;
    private final long redDuration;

    public TrafficLight(long green, long yellow, long red) {
        this.greenDuration = green;
        this.yellowDuration = yellow;
        this.redDuration = red;
    }

    public void nextState() {
        switch (currentState) {
            case RED -> currentState = LightState.GREEN;
            case GREEN -> currentState = LightState.YELLOW;
            case YELLOW -> currentState = LightState.RED;
        }
    }

    public LightState getCurrentState() {
        return currentState;
    }

    public long getCurrentDuration() {
        return switch (currentState) {
            case GREEN -> greenDuration;
            case YELLOW -> yellowDuration;
            case RED -> redDuration;
        };
    }

    public void reset() {
        currentState = LightState.RED;
    }
}