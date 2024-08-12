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

    private String city;

    private String cep;

    private String road;

    private int number;

    private String complement;

    public Address(String city, String cep, String road, int number, String complement){
        this.city = city;
        this.cep = cep;
        this.road = road;
        this.number = number;
        this.complement = complement;
    }
}
