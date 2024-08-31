package com.abracecdcAPI.abracecdcAPI.domain.user.dto;

import com.abracecdcAPI.abracecdcAPI.domain.user.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserRecordDTO(@NotBlank String name, @NotBlank String email, @NotBlank String password, @NotBlank String phone, @NotNull UserRole role) {
}
