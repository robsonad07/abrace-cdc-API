package com.abracecdcAPI.abracecdcAPI.domain.user;

import jakarta.persistence.Column;

public record RegisterDTO(String name, String email, String password, String phone, UserRole role) {
}
