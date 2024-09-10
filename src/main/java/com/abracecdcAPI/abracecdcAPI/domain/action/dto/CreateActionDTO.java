package com.abracecdcAPI.abracecdcAPI.domain.action.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateActionDTO {
  
  private UUID actionId;
  private UUID categoryId;
  private UUID organizerId;

  private String title;
  private String subtitle;
  private String description; 
  private float value;

  private LocalDateTime dateTime;
}
