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
public class BookHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hotelCode;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int nights;
    private int peopleQ;
    private Double price;
    private String roomType;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "bookHotel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> hosts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;
}
