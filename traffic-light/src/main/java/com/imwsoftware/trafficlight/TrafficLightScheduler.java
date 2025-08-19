package com.imwsoftware.trafficlight;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TrafficLightScheduler {

//    private final TrafficLightController controller;
private final DualTrafficLightController controller;
    private long nextTriggerTime;

    public TrafficLightScheduler(DualTrafficLightController controller) {
        this.controller = controller;
        // this.nextTriggerTime = System.currentTimeMillis();
        this.nextTriggerTime = System.currentTimeMillis() + controller.getCurrentDuration();
//        this.nextTriggerTime = System.currentTimeMillis() + controller.getNorthSouth().getCurrentDuration();

    }

    @Scheduled(fixedRate = 1000)
    public void advanceLight() {
        long now = System.currentTimeMillis();
        if (now >= nextTriggerTime) {
            controller.nextState();
//            long duration = controller.getCurrentDuration();
//            nextTriggerTime = now + duration;
//            System.out.println("State changed to: " + controller.getCurrentState() + " for " + duration + "ms");
//            nextTriggerTime = now + controller.getNorthSouth().getCurrentDuration(); // assume symmetric timing
            nextTriggerTime = now + controller.getCurrentDuration();

            logState();
        }
    }

    private void logState() {
        System.out.println("NS: " + controller.getNorthSouth()
                + ", EW: " + controller.getEastWest());
    }

    public long getMillisRemaining() {
        return Math.max(0, nextTriggerTime - System.currentTimeMillis());
    }

    public void reset() {
        controller.reset();  // ← we’ll add this to the controller next
        nextTriggerTime = System.currentTimeMillis() + controller.getCurrentDuration();
    }
}