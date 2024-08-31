package com.abracecdcAPI.abracecdcAPI.domain.register_action.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRegisterActionDTO {
  
  private UUID id;
  private UUID actionId;

  private String urlImage;
  private String description;
}
