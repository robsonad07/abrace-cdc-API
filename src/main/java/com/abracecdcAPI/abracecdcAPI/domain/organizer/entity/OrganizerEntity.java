package com.abracecdcAPI.abracecdcAPI.domain.organizer.entity;

import java.util.List;
import java.util.UUID;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
