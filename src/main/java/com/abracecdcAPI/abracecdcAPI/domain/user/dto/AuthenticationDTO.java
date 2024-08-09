package com.abracecdcAPI.abracecdcAPI.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank String email, @NotBlank String password) {
}
