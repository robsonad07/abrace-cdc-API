package com.abracecdcAPI.abracecdcAPI.domain.organizer.entity;

import java.util.List;
import java.util.UUID;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "organizers")
@Table(name = "organizers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizerEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @Column(unique = true)
  private String cellphone;

  @Column(unique = true)
  private String email;

  @OneToMany(mappedBy = "organizer")
  @JsonBackReference
  private List<Event> events;
}
