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
@JsonIgnoreProperties({"bookHotels"})
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hotelCode;
    private String name;
    private String place;
    private boolean isBooked;
    private String roomType;
    private Double roomPrice;
    private LocalDate disponibilityDateFrom;
    private LocalDate disponibilityDateTo;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<BookHotel> bookHotels = new ArrayList<>();

}