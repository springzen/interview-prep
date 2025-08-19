package com.imwsoftware.microservice.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String name,
        @Email String email,
        @NotBlank String role
) {}