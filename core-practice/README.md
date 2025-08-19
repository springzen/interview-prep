# Core Practice – Main Spring Boot Application

This is the primary application in the multi-module project built as part of interview preparation.

## Features

- RESTful endpoints with Spring Boot 3.5.4
- External HTTP calls using Spring's `WebClient`
- Swagger/OpenAPI documentation
- H2 in-memory database
- Resilience4j Circuit Breaker (optional)
- Proper exception handling and logging

## How to Run

```bash
./mvnw clean spring-boot:run -pl core-practice
```
Or run it via IDE (IntelliJ recommended).
App runs on: http://localhost:8989

##  Swagger API Docs

Once the app is running:
* Swagger UI: http://localhost:8989/swagger-ui.html
* OpenAPI Spec: http://localhost:8989/v3/api-docs

## Sample Endpoint
```GET /api/invoices```
Returns a list of mock invoices.

## Stack
* Java 17
* Spring Boot 3.5.4
* Spring Web
* H2 Database
* Swagger (springdoc-openapi)
* Lombok

## Purpose

* This application demonstrates:
* Clean API design and documentation
* Modular code structure
* Resilience and error handling
* Readiness for real-world production integration

