package com.abracecdcAPI.abracecdcAPI.domain.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(@NotBlank String city, @NotBlank String cep, @NotBlank String road, @NotNull int number, @NotBlank String complement) {
}
