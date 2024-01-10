package com.hackaboss.agenciaTurismo.controller;

import com.hackaboss.agenciaTurismo.dto.BookFlightDto;
import com.hackaboss.agenciaTurismo.model.BookFlight;
import com.hackaboss.agenciaTurismo.model.BookHotel;
import com.hackaboss.agenciaTurismo.model.User;
import com.hackaboss.agenciaTurismo.service.IBookFlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class BookFlightController {
    private static final String TEXT_PATTERN = "[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ ]+";
    private static final String ERROR_CODE = "Please enter a valid hotel ID (numbers only)";
    private static final String PASSPORT_PATTERN = "^[A-Za-z0-9]{6,15}$";
    private static final String INVALID_USER_NAME = "Invalid user name";
    private static final String INVALID_USER_LASTNAME = "Invalid user lastname";
    private static final String INVALID_USER_PASSPORT = "Invalid user passport";
    private static final String INVALID_USER_AGE = "Invalid user age";
    private static final String ERROR_CODE_NULL = "Flight code is required";
    private static final String ERROR_SEATTYPE_ERROR = "Error: Seat type does not match.";

    @Autowired
    private IBookFlightService bookFlightServ;

    @Operation(summary = "Flight reservation",
            description = "This method creates a flight reservation. Returns different HTTP response codes depending on the operation result.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight reservation created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid flight data"),
            @ApiResponse(responseCode = "500", description = "Server internal error")})
    @PostMapping("/flight-booking/new")
    public ResponseEntity<?> createBookFlight(@RequestBody BookFlightDto bookFlightDto) {
        try {
            String checkResult = check(bookFlightDto);
            if (checkResult != null) {
                return ResponseEntity.badRequest().body(checkResult);
            }

            bookFlightServ.createBookingFlight(bookFlightDto);

            Double totalPrice = bookFlightDto.getPrice() * bookFlightDto.getPassengers().size();

            return ResponseEntity.ok().body("The total price of the reservation is: " + totalPrice +" €");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get flight reservations",
            description = "This method shows all existing reservations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flight reservations."),
            @ApiResponse(responseCode = "400", description = "Invalid request or no flight reservations found."),
            @ApiResponse(responseCode = "500", description = "Server internal error")})
    @GetMapping("/flight-booking")
    public ResponseEntity<?> getAllBookFlights() {
        try {
            List<BookFlight> bookFlights = bookFlightServ.getAllBookFlights();
            return ResponseEntity.ok().body(bookFlights);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get a flight reservation by ID",
            description = "This method returns the information about a specific flight reservation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight reservation found successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request or flight reservation not found."),
            @ApiResponse(responseCode = "500", description = "Server internal error")})
    @GetMapping("/flight-booking/{id}")
    public ResponseEntity<?> getBookFlight(@PathVariable String id) {
        try {
            if (!id.matches("\\d+")) {
                return ResponseEntity.badRequest().body(ERROR_CODE);
            }

            Long bookFlightId = Long.valueOf(id);

            BookFlight bookFlight = bookFlightServ.getFlightById(bookFlightId);

            return ResponseEntity.ok().body(bookFlight);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete a flight reservation by ID",
            description = "This method deletes a specific flight reservation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight reservation deleted successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request or flight reservation not found."),
            @ApiResponse(responseCode = "500", description = "Server internal error")})
    @DeleteMapping("/flight-booking/delete/{id}")
    public ResponseEntity<?> deleteBookFlightById(@PathVariable String id) {
        try {
            if (!id.matches("\\d+")) {
                return ResponseEntity.badRequest().body(ERROR_CODE);
            }
            BookFlight deletedBookFlight = bookFlightServ.deleteBookingFlightById(Long.parseLong(id));
            return ResponseEntity.ok().body("Flight reservation with ID " + id + " deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Edit a flight reservation",
            description = "This method edits a specific flight reservation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight reservation edited successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request or flight reservation not found."),
            @ApiResponse(responseCode = "500", description = "Server internal error")})
    @PutMapping("/flight-booking/edit/{id}")
    public ResponseEntity<?> editBookFlight(@PathVariable String id, @RequestBody BookFlightDto bookFlightDto) {
        try {
            if (!id.matches("\\d+")) {
                return ResponseEntity.badRequest().body(ERROR_CODE);
            }

            Long bookFlightId = Long.valueOf(id);

            BookFlight editedBookFlight = bookFlightServ.editBookingFlight(bookFlightId, bookFlightDto);

            return ResponseEntity.ok().body("Flight reservation with ID " + id + " edited successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Metodo para responder a los posibles errores
    public String check(BookFlightDto bookFlightDto) {
        if (bookFlightDto.getDate() == null) {
            return "Date data is required";
        }
        if (bookFlightDto.getFlightCode() == null) {
            return ERROR_CODE_NULL;
        }

        String seatType = bookFlightDto.getSeatType();

        if (!seatType.matches(TEXT_PATTERN)) {
            return ERROR_SEATTYPE_ERROR;
        }

        double price = bookFlightDto.getPrice();
        if (price <= 0) {
            return "Price cannot be negative or 0";
        }
        return checkUserFlight(bookFlightDto.getPassengers());
    }

    //Metodo para responder a los posibles errores en la creacion del usuario

    private String checkUserFlight(List<User> users) {
        if (users == null || users.isEmpty()) {
            return "User information is required";
        }
        if (users.get(0).getName() == null || !users.get(0).getName().matches(TEXT_PATTERN)) {
            return INVALID_USER_NAME;
        }
        if (users.get(0).getLastName() == null || !users.get(0).getLastName().matches(TEXT_PATTERN)) {
            return INVALID_USER_LASTNAME;
        }
        if (users.get(0).getPassport() == null || !users.get(0).getPassport().matches(PASSPORT_PATTERN)) {
            return INVALID_USER_PASSPORT;
        }
        if (users.get(0).getAge() <= 0) {
            return INVALID_USER_AGE;
        }
        return null;
    }
}
