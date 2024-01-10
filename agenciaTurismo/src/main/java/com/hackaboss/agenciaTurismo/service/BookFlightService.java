package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.dto.BookFlightDto;
import com.hackaboss.agenciaTurismo.model.BookFlight;
import com.hackaboss.agenciaTurismo.model.Flight;
import com.hackaboss.agenciaTurismo.model.User;
import com.hackaboss.agenciaTurismo.repository.BookFlightRepository;
import com.hackaboss.agenciaTurismo.repository.FlightRepository;
import com.hackaboss.agenciaTurismo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookFlightService implements IBookFlightService{

    @Autowired
    private BookFlightRepository bookFlightRep;
    @Autowired
    private FlightRepository flightRep;
    @Autowired
    private UserRepository userRep;
    @Autowired
    private IUserService userServ;

    @Override
    public BookFlight createBookingFlight(BookFlightDto flightDto) {
        //Inicializamos bookFlight y users
        BookFlight bookFlight = new BookFlight();
        List<User> passengers = new ArrayList<>();

        //Obtenemos el vuelo existente con el codigo, el origen y tipo de asiento
        //ya que el mismo codigo se usa para el vuelo de ida y el de vuelta y para los diferentes asientos.
        Flight flightExist = flightRep.findByFlightNumberAndOriginAndSeatType(flightDto.getFlightCode(), flightDto.getOrigin(),flightDto.getSeatType());

        //Realizamos las validaciones específicas para este método
        if (flightExist == null) {
            throw new IllegalArgumentException("The flight is not registered in the database");
        }

        if (!flightExist.getDate().isEqual(flightDto.getDate())) {
            throw new IllegalArgumentException("The flight date is not valid");
        }

        //Creamos los pasajeros si no existen en la base de datos
        for (User userDto : flightDto.getPassengers()) {
            User passenger = userServ.findByPassport(userDto.getPassport());

            if (passenger == null) {
                passenger = new User();
            }

            passenger.setName(userDto.getName());
            passenger.setLastName(userDto.getLastName());
            passenger.setPassport(userDto.getPassport());
            passenger.setAge(userDto.getAge());

            userServ.createUser(passenger);

            passengers.add(passenger);
        }

        //Verificamos que la fecha introducida es la del vuelo
        if (flightDto.getDate().isAfter(LocalDate.now()) || flightDto.getDate().isEqual(LocalDate.now())) {
            bookFlight.setDate(flightDto.getDate());
            bookFlight.setOrigin(flightExist.getOrigin());
            bookFlight.setDestination(flightExist.getDestination());
            bookFlight.setFlightCode(flightExist.getFlightNumber());
            bookFlight.setPeopleQ(passengers.size());
            bookFlight.setSeatType(flightDto.getSeatType());

            double priceTotal = (passengers.size() * flightDto.getPrice());
            bookFlight.setPrice(priceTotal);
            bookFlight.setPassengers(passengers);
            bookFlight.setFlight(flightExist);

            //Comprobamos si algún pasajero ya tiene una reserva para la fecha del vuelo
            List<BookFlight> existingsBookings = bookFlightRep.getUserExistingBookings(passengers, flightDto.getDate());

            if (!existingsBookings.isEmpty()) {
                throw new IllegalArgumentException("A passenger has a reservation for the specified date.");
            }else {
                return bookFlightRep.save(bookFlight);
            }
        } else {
            throw new IllegalArgumentException("The flight date is not valid");
        }
    }


    @Override
    public List<BookFlight> getAllBookFlights() {
        List<BookFlight> bookFlights = bookFlightRep.findAll();

        if (!bookFlights.isEmpty()){
            return bookFlights;
        }else{
            throw new IllegalArgumentException("There are no booked flights.");
        }
    }

    @Override
    public BookFlight getFlightById(Long id) {
        Optional<BookFlight> optionalBookFlight = bookFlightRep.findById(id);
        if (optionalBookFlight.isPresent()) {
            BookFlight bookFlight = optionalBookFlight.get();
            return bookFlight;
        }else{
            throw new IllegalArgumentException("The flight reservation does not exist");
        }
    }

    @Override
    public BookFlight deleteBookingFlightById(Long id) {
        Optional<BookFlight> optionalBookFlight = bookFlightRep.findById(id);
        if (optionalBookFlight.isPresent()) {
            BookFlight bookFlight = optionalBookFlight.get();
            bookFlightRep.deleteById(id);
            return bookFlight;
        }else{
            throw new IllegalArgumentException("The flight reservation does not exist");
        }
    }

    @Override
    public BookFlight editBookingFlight(Long id, BookFlightDto flightDto) {
        Optional<BookFlight> optionalBookFlight = bookFlightRep.findById(id);

        if (optionalBookFlight.isPresent()) {
            BookFlight existingBooking = optionalBookFlight.get();
            Flight flightExist = flightRep.findByFlightNumberAndOriginAndSeatType(
                    flightDto.getFlightCode(), flightDto.getOrigin(), flightDto.getSeatType());

            //Realizamos las validaciones específicas para la edición
            if (flightExist == null) {
                throw new IllegalArgumentException("The flight is not registered in the database");
            }

            if (!flightExist.getDate().isEqual(flightDto.getDate())) {
                throw new IllegalArgumentException("The flight date is not valid");
            }

            //Actualizamos los datos de la reserva existente con los nuevos datos proporcionados
            existingBooking.setDate(flightDto.getDate());
            existingBooking.setOrigin(flightExist.getOrigin());
            existingBooking.setDestination(flightExist.getDestination());
            existingBooking.setFlightCode(flightExist.getFlightNumber());
            existingBooking.setSeatType(flightDto.getSeatType());

            List<User> updatedPassengers = new ArrayList<>();

            for (User userDto : flightDto.getPassengers()) {
                User passenger = userServ.findByPassport(userDto.getPassport());

                if (passenger == null) {
                    passenger = new User();
                }

                passenger.setName(userDto.getName());
                passenger.setLastName(userDto.getLastName());
                passenger.setPassport(userDto.getPassport());
                passenger.setAge(userDto.getAge());

                userServ.createUser(passenger);

                updatedPassengers.add(passenger);
            }

            existingBooking.setPassengers(updatedPassengers);
            existingBooking.setFlight(flightExist);


            return bookFlightRep.save(existingBooking);
        } else {
            throw new IllegalArgumentException("The flight reservation does not exist");
        }
    }
}
