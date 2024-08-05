package com.abracecdcAPI.abracecdcAPI.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotBlank String name, @NotBlank String email, @NotBlank String password, @NotBlank String phone, UserRole role) {
}
