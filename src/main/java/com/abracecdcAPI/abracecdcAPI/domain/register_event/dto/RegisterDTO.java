package com.abracecdcAPI.abracecdcAPI.domain.register_event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RegisterDTO(@NotBlank String urlImage, @NotBlank String description, @NotNull UUID event_id) {
}
