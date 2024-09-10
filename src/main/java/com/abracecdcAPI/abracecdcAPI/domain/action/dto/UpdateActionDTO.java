package com.abracecdcAPI.abracecdcAPI.domain.action.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity.DonationActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.entity.RegisterActionEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateActionDTO {

  private UUID id;
  private UUID categoryId;
  private UUID organizerId;

  private String title;
  private String subtitle;
  private String description;
  
  private List<RegisterActionEntity> registers;
  private List<DonationActionEntity> donationsAction;

  private LocalDateTime dateTime;
}
