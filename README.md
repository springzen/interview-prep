# 🧪 Interview Project – Spring Boot Multi-Module Project

This repository contains a set of Spring Boot 3.5.4 projects used to demonstrate backend development, modular design, and API documentation using Swagger/OpenAPI.

## 📁 Project Structure

- **core-practice**  
  Main application demonstrating Spring Boot, REST endpoints, H2 integration, Swagger documentation, and external service integration via `WebClient`.

- **diagnostics-common**  
  Shared library used across other modules for common utilities and diagnostics.

- **invoice-service**  
  Sample billing/invoice microservice using Spring Boot, MySQL, and Kafka integration (WIP or stub, depending on status).

- **traffic-light**  
  Toy problem implementation (e.g., state machine or logic service) for demonstration purposes and system design discussion.

- **diagrams**  
  Contains C4 and other sample architecture diagrams (e.g., Mermaid, Structurizr DSL) to support design discussions.

## 🚀 Getting Started

This project uses Maven for build and module management.

```bash
# Clone the project
git clone https://github.com/YOUR_USERNAME/core-practice.git
cd core-practice

# Run the main service (core-practice)
./mvnw clean spring-boot:run -pl core-practice