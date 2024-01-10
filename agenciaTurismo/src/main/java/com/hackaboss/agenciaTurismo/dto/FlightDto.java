package com.hackaboss.agenciaTurismo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightDto {
    private String flightNumber;
    private String origin;
    private String destination;
    private String seatType;
    private Double price;
    private LocalDate date;
}
