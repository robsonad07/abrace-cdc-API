package com.abracecdcAPI.abracecdcAPI.domain.category_action.entity;

import java.util.List;
import java.util.UUID;

import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "categories")
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<DonationEvent> donationEvents;

}