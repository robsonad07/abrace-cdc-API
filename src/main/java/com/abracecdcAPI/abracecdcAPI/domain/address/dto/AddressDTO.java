package com.abracecdcAPI.abracecdcAPI.domain.address.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AddressDTO( UUID id ,@NotBlank String city, @NotBlank String cep, @NotBlank String road, @NotNull int number, @NotBlank String complement) {
}
