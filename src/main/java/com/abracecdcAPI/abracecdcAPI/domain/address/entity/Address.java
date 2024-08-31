package com.abracecdcAPI.abracecdcAPI.domain.address.entity;

import java.util.List;
import java.util.UUID;

import com.abracecdcAPI.abracecdcAPI.domain.event.entity.Event;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "address")
@Entity(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
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
