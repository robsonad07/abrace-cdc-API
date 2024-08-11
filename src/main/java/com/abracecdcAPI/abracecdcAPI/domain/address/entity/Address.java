package com.abracecdcAPI.abracecdcAPI.domain.address.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name = "address")
@Entity(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String cidade;

    private String cep;

    private String rua;

    private int numero;

    private String complemento;
}
