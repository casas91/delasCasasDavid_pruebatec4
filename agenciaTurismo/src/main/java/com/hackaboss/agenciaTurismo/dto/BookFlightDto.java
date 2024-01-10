package com.hackaboss.agenciaTurismo.dto;

import com.hackaboss.agenciaTurismo.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookFlightDto {
    private LocalDate date;
    private String origin;
    private String destination;
    private String flightCode;
    private String seatType;
    private double price;
    private List<User> passengers;

}
