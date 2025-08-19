package com.imwsoftware.diagnostics;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Endpoint(id = "java-version")
public class JavaVersionEndpoint {

    @ReadOperation
    public Map<String, String> javaVersionInfo() {
        return Map.of(
            "javaVersion", System.getProperty("java.version"),
            "vendor", System.getProperty("java.vendor"),
            "home", System.getProperty("java.home")
        );
    }
}
