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
public class BookHotelDto {
    private String hotelCode;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String place;
    private String roomType;
    private List<User> hosts;
}
