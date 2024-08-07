package com.abracecdcAPI.abracecdcAPI.domain.organizer_event.entity;

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

@Entity(name = "organizers_events")
@Table(name = "organizers_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerEventEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @Column(unique = true)
  private String cellphone;

  @Column(unique = true)
  private String email;
}
