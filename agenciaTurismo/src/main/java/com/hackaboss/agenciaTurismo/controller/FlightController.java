package com.hackaboss.agenciaTurismo.controller;

import com.hackaboss.agenciaTurismo.dto.FlightDto;
import com.hackaboss.agenciaTurismo.model.Flight;
import com.hackaboss.agenciaTurismo.service.IFlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class FlightController {

    @Autowired
    private IFlightService flightServ;

    @PostMapping(("flights/new"))
    @Operation(summary = "Create a flight",
            description = "This endpoint creates a hotel. It shows different responses depending on the result.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Some parameter does not meet the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> saveFlight(@RequestBody FlightDto flightDto){
        //Validaciones
        if (flightDto.getOrigin() == null || flightDto.getOrigin().isEmpty() || !flightDto.getOrigin().matches("[a-zA-Z]+")) {
            return ResponseEntity.badRequest().body("Flight origin can only contain letters and it can´t be empty");
        }

        if (flightDto.getDestination() == null || flightDto.getDestination().isEmpty() || !flightDto.getDestination().matches("[a-zA-Z]+")) {
            return ResponseEntity.badRequest().body("Flight destination can only contain letters and it can´t be empty");
        }

        if (flightDto.getSeatType() == null || !flightDto.getSeatType().matches("(?i)economy|business")) {
            return ResponseEntity.badRequest().body("Seat type can only be: economy or business.");
        }

        if (flightDto.getPrice() != null && flightDto.getPrice() < 0 ) {
            return ResponseEntity.badRequest().body("Flight price cannot be negative");
        }

        flightServ.createFlight(flightDto);
        return ResponseEntity.ok("The flight has been succesfully created.");
    }

    @GetMapping("/flights")
    @Operation(summary = "List of flights",
            description = "This endpoint shows all the flights")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> getAllFlights() {
        List<Flight> flights = flightServ.getAllFlights();

        if (flights.isEmpty()) {
            return ResponseEntity.badRequest().body("No flights found.");
        } else {
            return ResponseEntity.ok().body(flights);
        }
    }

    @GetMapping("/flights/search")
    @Operation(summary = "Flights available between dates and place.",
            description = "This endpoint displays all flights within the given dates, origin and destination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> getAllFlightsByPlaceAndDate (@RequestParam LocalDate date1,
                                                          @RequestParam LocalDate date2,
                                                          @RequestParam String origin,
                                                          @RequestParam String destination) {
        List<Flight> flights = flightServ.getAllFlightsByPlaceAndDate(date1, date2, origin, destination);

        if (flights.isEmpty()) {
            return ResponseEntity.badRequest().body("No flights found for the given criteria.");
        } else {
            return ResponseEntity.ok().body(flights);
        }
    }

    @PutMapping("/flights/edit/{id}")
    @Operation(summary = "Edit a flight",
            description = "This endpoint edits the flight identified by the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> editFlightById(@PathVariable Long id, @RequestBody FlightDto flightDto){

        if (flightServ.editFlightById(id, flightDto) == null) {
            return ResponseEntity.badRequest().body("Flight not found");
        }

        // Verificar si el hotel tiene reservas
        if (!flightServ.getFlightById(id).getBookFlights().isEmpty()) {
            return ResponseEntity.badRequest().body("Flight has reservations");
        }

        // Resto del código para editar el hotel
        flightServ.editFlightById(id, flightDto);
        return ResponseEntity.ok().body("Flight edited");
    }

    @GetMapping("/flights/{id}")
    @Operation(summary = "Flight by ID",
    description = "This endpoint deletes the flight with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> getFlightById(@PathVariable Long id) {
        if (flightServ.getFlightById(id) == null) {
            return ResponseEntity.badRequest().body("The flight does not exist");
        }

        return ResponseEntity.ok().body(flightServ.getFlightById(id));
    }

    @DeleteMapping("/flights/delete/{id}")
    @Operation(summary = "Delete a flight",
            description = "This endpoint displays the flight with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> deleteFlightById(@PathVariable Long id) {
        Flight deletedFlight = flightServ.deleteFlightById(id);

        if (deletedFlight == null) {
            return ResponseEntity.badRequest().body("Flight not found");
        }

        if (!deletedFlight.getBookFlights().isEmpty()) {
            return ResponseEntity.badRequest().body("Flight has reservations");
        }

        return ResponseEntity.ok().body("Flight deleted");
    }

}
