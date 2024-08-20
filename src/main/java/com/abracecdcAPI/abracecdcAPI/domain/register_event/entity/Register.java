package com.abracecdcAPI.abracecdcAPI.domain.register_event.entity;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @JsonBackReference
    private Event event;

    public Register(String urlImage, String description, Event event){
        this.urlImage = urlImage;
        this.description = description;
        this.event = event;
    }

    public Register(UUID id,String urlImage, String description){
        this.id = id;
        this.urlImage = urlImage;
        this.description = description;
    }
}
