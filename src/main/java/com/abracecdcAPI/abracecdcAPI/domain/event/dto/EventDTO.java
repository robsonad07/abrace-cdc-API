package com.abracecdcAPI.abracecdcAPI.domain.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDTO(@NotBlank String title, @NotBlank String caption, @NotBlank String description, @NotBlank LocalDateTime dateTime,
                       @NotNull UUID category_id, @NotNull UUID organizer_id, @NotNull UUID address_id, @NotNull UUID register_id) {
}
