package com.abracecdcAPI.abracecdcAPI.domain.action.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.category_action.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register.entity.Register;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "actions")
@Table(name = "actions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String title;
  private String subtitle;
  private String description;
  private String duration;

  @Column(name = "date_time", columnDefinition = "TIMESTAMP")
  private LocalDateTime dateTime;

  @ManyToOne()
  @JoinColumn(name = "category_id")
  private CategoryEntity categoryEntity;

  @ManyToOne()
  @JoinColumn(name = "organizer_id")
  private OrganizerEntity organizerEntity;

  @ManyToOne()
  @JoinColumn(name = "address_id")
  private Address addressEntity;

  @OneToMany(mappedBy = "action")
  @JsonManagedReference
  private List<Register> registers;

}
