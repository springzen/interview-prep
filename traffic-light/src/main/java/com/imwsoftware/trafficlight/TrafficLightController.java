package com.imwsoftware.trafficlight;

public interface TrafficLightController {
    void nextState();
    LightState getCurrentState();
    long getCurrentDuration();

    void reset();
}