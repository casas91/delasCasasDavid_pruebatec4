package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.dto.FlightDto;
import com.hackaboss.agenciaTurismo.model.Flight;
import com.hackaboss.agenciaTurismo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class FlightService implements IFlightService{

    @Autowired
    private FlightRepository flightRep;

    @Override
    public Flight createFlight(FlightDto flightDto) {
        LocalDate currentDate = LocalDate.now();
        if (flightDto.getDate().isAfter(currentDate) || flightDto.getDate().isEqual(currentDate)) {
        Flight flight = new Flight();

        flight.setFlightNumber(flightDto.getFlightNumber());
        flight.setOrigin(flightDto.getOrigin());
        flight.setDestination(flightDto.getDestination());
        flight.setSeatType(flightDto.getSeatType());
        flight.setDate(flightDto.getDate());
        flight.setPrice(flightDto.getPrice());

        return flightRep.save(flight);
    } else {
            return null;
        }
    }

    @Override
    public List<Flight> getAllFlightsByPlaceAndDate(LocalDate disponibilityDateFrom, LocalDate disponibilityDateTo, String origin, String destination) {
        return flightRep.getAllFlightsByPlaceAndDate(disponibilityDateFrom, disponibilityDateTo, origin, destination);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRep.findAll();
    }

    @Override
    public Flight deleteFlightById(Long id) {
        Optional<Flight> optionalFlight = flightRep.findById(id);

        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();

            if (!flight.getBookFlights().isEmpty()) {
                //Si el hotel tiene reservas, no se puede eliminar
                return null;
            }

            flightRep.deleteById(id);
            return flight;
        }
        return null;
    }

    @Override
    public Flight editFlightById(Long id, FlightDto updatedFlightDto) {
        //Verificamos si el vuelo existe
        Flight existingFlight = flightRep.findById(id).orElse(null);

        if (existingFlight != null) {
            existingFlight.setFlightNumber(updatedFlightDto.getFlightNumber());
            existingFlight.setOrigin(updatedFlightDto.getOrigin());
            existingFlight.setDestination(updatedFlightDto.getDestination());
            existingFlight.setSeatType(updatedFlightDto.getSeatType());
            existingFlight.setDate(updatedFlightDto.getDate());

            return flightRep.save(existingFlight);
        } else {
            return null;
        }
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRep.findById(id).orElse(null);
    }
}
