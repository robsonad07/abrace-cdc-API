package com.abracecdcAPI.abracecdcAPI.domain.organizer.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "organizers")
@Table(name = "organizers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @Column(unique = true)
  private String cellphone;

  @Column(unique = true)
  private String email;
}
