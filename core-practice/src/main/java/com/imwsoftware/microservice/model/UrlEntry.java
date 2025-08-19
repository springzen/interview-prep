package com.imwsoftware.microservice.model;

import java.time.Instant;

public record UrlEntry(String code, String longUrl, Instant createdAt) {}