package com.abracecdcAPI.abracecdcAPI.domain.register.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@NotBlank String urlImage, @NotBlank String description) {
}
