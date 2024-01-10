package com.hackaboss.agenciaTurismo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDto {
    private String hotelCode;
    private String name;
    private String place;
    private String roomType;
    private Double roomPrice;
    private LocalDate disponibilityDateFrom;
    private LocalDate disponibilityDateTo;
}
