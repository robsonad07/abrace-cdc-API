package com.abracecdcAPI.abracecdcAPI.domain.event.entity;

import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.register_event.entity.Register;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "event")
@Entity(name = "event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String caption;
    private String description;

    @Column(name="date_time", columnDefinition = "TIMESTAMP")
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

    @OneToMany(mappedBy = "event")
    @JsonManagedReference
    private List<Register> registers;

    @OneToMany(mappedBy = "event")
    @JsonBackReference
    private List<DonationEvent> donationEvents;



    public Event(String title, String caption, String description, LocalDateTime dateTime,
                 CategoryEntity category, OrganizerEntity organizer, Address address) {
        this.title = title;
        this.caption = caption;
        this.description = description;
        this.dateTime = dateTime;
        this.category = category;
        this.organizer = organizer;
        this.address = address;
    }


}
