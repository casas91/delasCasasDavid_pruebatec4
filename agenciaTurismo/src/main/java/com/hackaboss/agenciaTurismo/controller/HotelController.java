package com.hackaboss.agenciaTurismo.controller;

import com.hackaboss.agenciaTurismo.dto.HotelDto;
import com.hackaboss.agenciaTurismo.model.Hotel;
import com.hackaboss.agenciaTurismo.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class HotelController {

    @Autowired
    private IHotelService hotelServ;

    @PostMapping(("hotels/new"))
    @Operation(summary = "Create a hotel",
            description = "This endpoint creates a hotel. It shows different responses depending on the result.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Some parameter does not meet the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?>saveHotel(@RequestBody HotelDto hotelDto){
        //Validaciones

        if (hotelDto.getPlace() == null || hotelDto.getPlace().isEmpty() || !hotelDto.getPlace().matches("[a-zA-Z]+")) {
            return ResponseEntity.badRequest().body("Hotel place can only contain letters and it can´t be empty");
        }

        if (hotelDto.getRoomType() == null || !hotelDto.getRoomType().matches("(?i)single|double|triple")) {
            return ResponseEntity.badRequest().body("Room type can only be: single, double, or triple.");
        }

        if (hotelDto.getDisponibilityDateFrom() == null || hotelDto.getDisponibilityDateTo() == null ||
                hotelDto.getDisponibilityDateFrom().isBefore(LocalDate.now()) ||
                hotelDto.getDisponibilityDateTo().isBefore(hotelDto.getDisponibilityDateFrom()) ||
                hotelDto.getDisponibilityDateTo().isBefore(LocalDate.now())) {

            return ResponseEntity.badRequest().body("Invalid availability dates.");
        }

        if (hotelDto.getRoomPrice() != null && hotelDto.getRoomPrice() < 0 ) {
            return ResponseEntity.badRequest().body("Room price cannot be negative");
        }

        hotelServ.createHotel(hotelDto);
        return ResponseEntity.ok("The hotel has been succesfully created.");
    }

    @GetMapping("/hotels")
    @Operation(summary = "List of Hotels",
            description = "This endpoint shows all the hotels.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> getAllHotels() {
        List<Hotel> hotels = hotelServ.getAllHotels();

        if (hotels.isEmpty()) {
            return ResponseEntity.badRequest().body("No Hotel found.");
        } else {
            return ResponseEntity.ok().body(hotels);
        }
    }


    @GetMapping("/hotels/search")
    @Operation(summary = "Hotel available between dates and place.",
            description = "This endpoint displays all hotels within the given dates and city.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> getAvailableHotels(
            @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate disponibilityDateFrom,
            @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate disponibilityDateTo,
            @RequestParam("place") String place,
            @RequestParam("isBooked") Boolean isBooked)
    {

        List<Hotel> availableHotels = hotelServ.getHotelsByDatesAndPlace(disponibilityDateFrom, disponibilityDateTo, place, isBooked);

        if (availableHotels.isEmpty()) {
            return  ResponseEntity.badRequest().body("No hotel found for the given criteria.");
        } else {
            return  ResponseEntity.ok().body(availableHotels);
        }
    }

    @GetMapping("/hotels/{id}")
    @Operation(summary = "Hotel by ID",
            description = "This endpoint displays the hotel with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> getHotelById(@PathVariable Long id) {
        if (hotelServ.getHotelById(id) == null) {
            return ResponseEntity.badRequest().body("The hotel does not exist");
        }

        return ResponseEntity.ok().body(hotelServ.getHotelById(id));
    }

    @PutMapping("/hotels/edit/{id}")
    @Operation(summary = "Edit a hotel",
            description = "This endpoint edits the hotel identified by the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Some parameter does not meet the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> editHotelById(@PathVariable Long id, @RequestBody HotelDto hotelDto){

        if (hotelServ.editHotelById(id, hotelDto) == null) {
            return ResponseEntity.badRequest().body("Hotel not found");
        }

        // Verificar si el hotel tiene reservas
        if (!hotelServ.getHotelById(id).getBookHotels().isEmpty()) {
            return ResponseEntity.badRequest().body("Hotel has reservations");
        }

        // Resto del código para editar el hotel
        hotelServ.editHotelById(id, hotelDto);
        return ResponseEntity.ok().body("Hotel edited");
    }

    @DeleteMapping("/hotels/delete/{id}")
    @Operation(summary = "Delete a hotel",
            description = "This endpoint deletes the hotel identified by the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> deleteHotelById(@PathVariable Long id) {
        Hotel deletedHotel = hotelServ.deleteHotelById(id);

        if (deletedHotel == null) {
            return ResponseEntity.badRequest().body("Hotel not found");
        }

        if (!deletedHotel.getBookHotels().isEmpty()) {
            return ResponseEntity.badRequest().body("Hotel has reservations");
        }

        return ResponseEntity.ok().body("Hotel deleted");
    }

}
