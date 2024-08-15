package com.abracecdcAPI.abracecdcAPI.domain.register.entity;

import com.abracecdcAPI.abracecdcAPI.domain.donation_event.entity.DonationEvent;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "register")
@Entity(name = "register")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String urlImage;
    private String description;

    @OneToMany(mappedBy = "register")
    private List<DonationEvent> donationEvents;

    public Register(String urlImage, String description){
        this.urlImage = urlImage;
        this.description = description;
    }

    public Register(UUID id,String urlImage, String description){
        this.id = id;
        this.urlImage = urlImage;
        this.description = description;
    }
}
