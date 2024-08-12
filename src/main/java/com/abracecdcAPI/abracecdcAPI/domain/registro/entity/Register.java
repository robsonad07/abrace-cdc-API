package com.abracecdcAPI.abracecdcAPI.domain.registro.entity;

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
    private String url_image;
    private String description;

    public Register(String url_image, String description){
        this.url_image = url_image;
        this.description = description;
    }
}
