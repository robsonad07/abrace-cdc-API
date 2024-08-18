package com.abracecdcAPI.abracecdcAPI.domain.address.entity;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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

    @OneToMany(mappedBy = "address")
    @JsonBackReference
    private List<Event> events;

    public Address(String city, String cep, String road, int number, String complement){
        this.city = city;
        this.cep = cep;
        this.road = road;
        this.number = number;
        this.complement = complement;
    }

    public Address(UUID id, String city, String cep, String road, int number, String complement){
        this.id = id;
        this.city = city;
        this.cep = cep;
        this.road = road;
        this.number = number;
        this.complement = complement;
    }
}
