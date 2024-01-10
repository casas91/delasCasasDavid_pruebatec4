package com.hackaboss.agenciaTurismo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"bookFlights"})
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNumber;
    private String origin;
    private String destination;
    private String seatType;
    private Double price;
    private LocalDate date;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<BookFlight> bookFlights = new ArrayList<>();
}
