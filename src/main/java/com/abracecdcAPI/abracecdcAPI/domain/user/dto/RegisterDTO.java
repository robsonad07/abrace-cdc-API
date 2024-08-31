package com.abracecdcAPI.abracecdcAPI.domain.user.dto;

import com.abracecdcAPI.abracecdcAPI.domain.user.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;



@Builder
public record RegisterDTO(@NotBlank String name, @NotBlank String email, @NotBlank String password, @NotBlank String phone, UserRole role) {
}
