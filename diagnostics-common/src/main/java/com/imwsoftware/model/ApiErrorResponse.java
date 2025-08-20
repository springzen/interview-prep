package com.imwsoftware.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApiErrorResponse {
    private final String error;
    private final String message;
    private final int status;
    private final String path;
    private final LocalDateTime timestamp;

    public ApiErrorResponse(String error, String message, int status, String path) {
        this.error = error;
        this.message = message;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}