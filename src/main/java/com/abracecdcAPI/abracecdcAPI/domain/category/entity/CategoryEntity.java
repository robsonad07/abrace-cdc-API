package com.abracecdcAPI.abracecdcAPI.domain.category.entity;

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

@Entity(name = "categories")
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<Event> events;

}