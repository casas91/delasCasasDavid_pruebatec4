package com.hackaboss.agenciaTurismo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private int age;
    private String passport;


    @ManyToMany(mappedBy = "passengers", cascade = CascadeType.ALL)
    private List<BookFlight> bookFlights = new ArrayList<>();

    @ManyToMany(mappedBy = "hosts", cascade = CascadeType.ALL)
    private List<BookHotel> hotels = new ArrayList<>();

}
