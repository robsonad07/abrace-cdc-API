package com.abracecdcAPI.abracecdcAPI.domain.register_action.entity;

import java.util.UUID;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "registers_action")
@Entity(name = "registers_action")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterActionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String urlImage;
  private String description;

  @ManyToOne
  @JoinColumn(name = "action_id", nullable = false)
  @JsonBackReference
  private ActionEntity actionEntity;

}
