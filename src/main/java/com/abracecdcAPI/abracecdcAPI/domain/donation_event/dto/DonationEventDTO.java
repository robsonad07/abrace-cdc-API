package com.abracecdcAPI.abracecdcAPI.domain.donation_event.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DonationEventDTO(@NotNull Double value, UUID event_id, UUID user_id) {
}
