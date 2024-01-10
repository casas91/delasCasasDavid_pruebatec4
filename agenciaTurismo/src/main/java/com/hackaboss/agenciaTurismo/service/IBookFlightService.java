package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.dto.BookFlightDto;
import com.hackaboss.agenciaTurismo.model.BookFlight;

import java.util.List;

public interface IBookFlightService {

    BookFlight createBookingFlight(BookFlightDto booking);

    List<BookFlight> getAllBookFlights();

    BookFlight getFlightById(Long id);

    BookFlight deleteBookingFlightById(Long id);

    BookFlight editBookingFlight(Long id, BookFlightDto flightDto);


}
