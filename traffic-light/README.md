# Traffic Light – Toy Problem

This module is a partial implementation of a system design or state machine exercise. It simulates the logic of a traffic light controller and may be used for interviews or system design demonstrations.

## Goal

- Model a simple traffic light that changes state (e.g., RED → GREEN → YELLOW → RED)
- Implement state transitions, duration tracking, and RESTful observation
- Could be extended with time-based transitions or WebSocket simulation

## Status

🚧 Work in progress. Basic structure and controller logic are stubbed. More logic and tests to be added.

## Ideas for Next Steps

- Add configurable timing logic per state
- Implement `TrafficLightController` REST endpoints
- Add unit tests for state transitions
- Consider event-driven (Pub/Sub) version for fun