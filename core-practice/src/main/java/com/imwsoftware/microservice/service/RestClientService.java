package com.imwsoftware.microservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class RestClientService {
    private final WebClient webClient = WebClient.create();

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallback")
    public String callExternalService() {
        return webClient.get()
                .uri("https://httpbin.org/get")
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(1))
                .block();
    }

    @SuppressWarnings("unused")
    public String fallback(Throwable t) {
        return "Service temporarily unavailable. Please try again later.";
    }
}
