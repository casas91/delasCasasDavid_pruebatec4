package com.hackaboss.agenciaTurismo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BookFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String origin;
    private String destination;
    private String flightCode;
    private Integer peopleQ;
    private String seatType;
    private Double price;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "book_flight_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> passengers = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "id_flight")
    private Flight flight;

}

