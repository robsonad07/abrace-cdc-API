package com.abracecdcAPI.abracecdcAPI.domain.donation_action.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDonationActionDTO {
  
  private UUID id;
  private UUID userId;
  private UUID actionId;

  private int value;
}
