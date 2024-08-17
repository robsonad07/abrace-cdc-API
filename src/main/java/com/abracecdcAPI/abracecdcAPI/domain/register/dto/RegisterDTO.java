package com.abracecdcAPI.abracecdcAPI.domain.register.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RegisterDTO(@NotBlank String urlImage, @NotBlank String description, @NotNull UUID event_id) {
}
