package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.dto.FlightDto;
import com.hackaboss.agenciaTurismo.model.Flight;
import com.hackaboss.agenciaTurismo.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class FlightServiceTest {

    @Autowired
    private FlightService flightService;

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void createFlight_validFlightDto_returnsFlight() {
        //Arrange
        FlightDto validFlightDto = new FlightDto("ABC123", "Madrid", "Barcelona", "Economy", 500.0, LocalDate.now().plusDays(1));

        //Act
        Flight createdFlight = flightService.createFlight(validFlightDto);

        //Assert
        assertEquals(validFlightDto.getFlightNumber(), createdFlight.getFlightNumber());
        assertEquals(validFlightDto.getOrigin(), createdFlight.getOrigin());
        assertEquals(validFlightDto.getDestination(), createdFlight.getDestination());
        assertEquals(validFlightDto.getSeatType(), createdFlight.getSeatType());
        assertEquals(validFlightDto.getDate(), createdFlight.getDate());
        assertEquals(validFlightDto.getPrice(), createdFlight.getPrice());

        //Clean up
        flightRepository.delete(createdFlight);
    }

    @Test
    void createFlight_invalidDate_returnsNull() {
        //Arrange
        FlightDto invalidDateDto = new FlightDto("ABC123", "Madrid", "Barcelona", "Economy", 500.0, LocalDate.now().minusDays(1));

        //Act
        Flight resultFlight = flightService.createFlight(invalidDateDto);

        //Assert
        assertNull(resultFlight);
    }
}
