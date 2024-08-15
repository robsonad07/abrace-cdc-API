package com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.category_action.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register.entity.Register;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "donation_event")
@Entity(name = "donation_event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DonationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String caption;
    private String description;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private OrganizerEntity organizer;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "register_id", nullable = false)
    private Register register;

}
