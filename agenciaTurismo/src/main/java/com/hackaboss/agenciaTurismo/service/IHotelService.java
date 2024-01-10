package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.dto.HotelDto;
import com.hackaboss.agenciaTurismo.model.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface IHotelService {

    Hotel createHotel (HotelDto hotelDto);
    List<Hotel> getHotelsByDatesAndPlace(LocalDate disponibilityDateFrom, LocalDate disponibilityDateTo, String place, Boolean isBooked);

    List<Hotel> getAllHotels();

    Hotel deleteHotelById(Long id);

    Hotel editHotelById(Long id, HotelDto updatedHotelDto);

    Hotel getHotelById(Long id);
}
