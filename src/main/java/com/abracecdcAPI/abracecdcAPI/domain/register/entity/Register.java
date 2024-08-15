package com.abracecdcAPI.abracecdcAPI.domain.register.entity;

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

    public Register(String urlImage, String description){
        this.urlImage = urlImage;
        this.description = description;
    }
}
