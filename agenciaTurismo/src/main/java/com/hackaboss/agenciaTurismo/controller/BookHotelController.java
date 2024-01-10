package com.hackaboss.agenciaTurismo.controller;

import com.hackaboss.agenciaTurismo.dto.BookHotelDto;
import com.hackaboss.agenciaTurismo.model.BookHotel;
import com.hackaboss.agenciaTurismo.service.IBookHotelService;
import com.hackaboss.agenciaTurismo.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class BookHotelController {

    private static final String TEXT_PATTERN = "[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ ]+";

    private static final String ERROR_CODE = "Please enter a valid hotel ID (numbers only)";

    private static final String INVALID_PLACE_NAME = "Invalid place name";
    private static final String INVALID_ROOM_TYPE = "Invalid room type";
    private static final String INVALID_USER_NAME = "Invalid user name";
    private static final String INVALID_USER_LASTNAME = "Invalid user lastname";
    private static final String INVALID_USER_PASSPORT = "Invalid user passport";
    private static final String INVALID_USER_AGE = "Invalid user age";
    private static final String PASSPORT_PATTERN = "^[A-Za-z0-9]{6,15}$";
    private static final String DATE_ORDER_ERROR_START = "Error: Disponibility start date must be before end date.";
    private static final String DATE_ORDER_ERROR_END = "Error: Disponibility end date must be after start date.";

    @Autowired
    private IBookHotelService bookHotelServ;

    @Operation(summary = "Hotel reservation",
            description = "This method creates a hotel reservation. Returns different HTTP response codes depending on the operation result.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel reservation created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid hotel reservation data"),
            @ApiResponse(responseCode = "500", description = "Server internal error")})
    @PostMapping("/hotel-booking/new")
    public ResponseEntity<?> createBookHotel(@RequestBody BookHotelDto bookHotelDto){
        //Validamos los datos antes de crear la reserva
        String check = checkBookingDate(bookHotelDto);

        if (check != null) {
            return ResponseEntity.badRequest().body(check);
        }
        //Verificamos antes de crear la reserva
        int peopleQ = bookHotelDto.getHosts().size();
        String roomType = bookHotelDto.getRoomType();
        int maxPeopleAllowed = 0;

        if (roomType.equalsIgnoreCase("single")) {
            maxPeopleAllowed = 1;
        } else if (roomType.equalsIgnoreCase("double")) {
            maxPeopleAllowed = 2;
        } else if (roomType.equalsIgnoreCase("triple")) {
            maxPeopleAllowed = 3;
        } else {

            return ResponseEntity.badRequest().body("Invalid room type");
        }
        //Verificamos que la cantidad de personas no sea mayor que la permitida
        if (peopleQ > maxPeopleAllowed) {
            return ResponseEntity.badRequest().body("The " + roomType + " room allows a maximum of " + maxPeopleAllowed + " people.");
        }
        try {
            //Creamos reserva
            BookHotel bookedHotel = bookHotelServ.createBookHotel(bookHotelDto);

            //Devolvemos la respuesta con el totalPrice
            return ResponseEntity.ok().body("The total price of the reservation is: " + bookedHotel.getPrice() + " €");
        } catch (IllegalArgumentException e) {
            //Capturamos excepciones de validación y devolvemos una respuesta de error si existe algun error
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(summary = "Get hotel reservations",
            description = "This method shows all existing reservations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of hotel reservations."),
            @ApiResponse(responseCode = "400", description = "Invalid request or no hotel reservations found."),
            @ApiResponse(responseCode = "500", description = "Server internal error")})
    @GetMapping("/hotel-booking")
    public ResponseEntity<?> getAllBookHotels() {
        try {
            List<BookHotel> bookHotels = bookHotelServ.getAllBookHotels();
            return ResponseEntity.ok().body(bookHotels);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get a hotel reservation by ID",
            description = "This method returns the information about a specific hotel reservation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel reservation information."),
            @ApiResponse(responseCode = "400", description = "Invalid request or hotel reservation not found."),
            @ApiResponse(responseCode = "500", description = "Server internal error")})
    @GetMapping("/hotel-booking/{id}")
    public ResponseEntity<?> getBookHotelById(@PathVariable String id) {
        try {
            if (!id.matches("\\d+")) {
                return ResponseEntity.badRequest().body(ERROR_CODE);
            }
            BookHotel bookHotel = bookHotelServ.getBookHotelById(Long.parseLong(id));
            return ResponseEntity.ok().body(bookHotel);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete a hotel reservation by ID",
            description = "This method deletes a specific hotel reservation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel reservation deleted successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request or hotel reservation not found."),
            @ApiResponse(responseCode = "500", description = "Server internal error")})
    @DeleteMapping("/hotel-booking/delete/{id}")
    public ResponseEntity<?> deleteBookHotelById(@PathVariable String id) {
        try {
            if (!id.matches("\\d+")) {
                return ResponseEntity.badRequest().body(ERROR_CODE);
            }
            BookHotel deletedBookHotel = bookHotelServ.deleteBookHotelById(Long.parseLong(id));
            return ResponseEntity.ok().body("Hotel reservation with ID " + id + " was deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/hotel-booking/edit/{id}")
    @Operation(summary = "Edit a hotel reservation",
            description = "This method edits a hotel reservation identified by the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Some parameter does not meet the format or is required and is not present."),
            @ApiResponse(responseCode = "500", description = "Server internal error")
    })
    public ResponseEntity<?> editBookHotelById(@PathVariable String id, @RequestBody BookHotelDto bookHotelDto) {
        try {
            if (!id.matches("\\d+")) {
                return ResponseEntity.badRequest().body(ERROR_CODE);
            }

            BookHotel editedBookHotel = bookHotelServ.editBookHotel(Long.parseLong(id), bookHotelDto);

            //Devuelve la respuesta con el totalPrice
            return ResponseEntity.ok().body("The hotel reservation with ID " + id + " has been successfully edited. The total price is: " + editedBookHotel.getPrice() + " €");
        } catch (IllegalArgumentException e) {
            //Captura excepciones de validación y devuelve una respuesta de error si existe algún error
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //Metodo para responder a los posibles errores
        private String checkBookingDate(BookHotelDto bookHotelDto) {

        if (bookHotelDto.getDateFrom() == null || bookHotelDto.getDateTo() == null) {
            return "Date data is required";
        }
        if (bookHotelDto.getPlace() == null || !bookHotelDto.getPlace().matches(TEXT_PATTERN)) {
            return INVALID_PLACE_NAME;
        }
        if (bookHotelDto.getRoomType() == null || !bookHotelDto.getRoomType().matches(TEXT_PATTERN)) {
            return INVALID_ROOM_TYPE;
        }
        if (bookHotelDto.getDateFrom().isAfter(bookHotelDto.getDateTo())) {
            return DATE_ORDER_ERROR_START;
        }
        if (bookHotelDto.getDateTo().isAfter(bookHotelDto.getDateTo())) {
            return DATE_ORDER_ERROR_END;
        }
        return checkBookingUser(bookHotelDto.getHosts());
    }

    //Metodo para responder a los posibles errores en la creacion del usuario
    private String checkBookingUser(List<User> users) {

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
