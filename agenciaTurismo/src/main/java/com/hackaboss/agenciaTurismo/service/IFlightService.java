package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.dto.FlightDto;
import com.hackaboss.agenciaTurismo.model.Flight;

import java.time.LocalDate;
import java.util.List;

public interface IFlightService {
    Flight createFlight(FlightDto flightDto);

    List<Flight> getAllFlightsByPlaceAndDate(LocalDate disponibilityDateFrom, LocalDate disponibilityDateTo, String origin, String destination);

    List<Flight> getAllFlights();


    Flight deleteFlightById(Long id);

    Flight editFlightById(Long id, FlightDto updatedflightDto);

    Flight getFlightById(Long id);
}
