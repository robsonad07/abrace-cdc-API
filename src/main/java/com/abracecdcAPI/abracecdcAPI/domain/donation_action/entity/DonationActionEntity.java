package com.abracecdcAPI.abracecdcAPI.domain.donation_action.entity;

import java.util.UUID;

import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.user.entity.User;
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

@Entity(name = "donations_action")
@Table(name = "donations_action")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DonationActionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  
  @ManyToOne()
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "action_id")
  @JsonBackReference 
  private ActionEntity actionEntity;

  private int value;
}
