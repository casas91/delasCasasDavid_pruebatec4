package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.dto.BookHotelDto;
import com.hackaboss.agenciaTurismo.model.BookHotel;
import com.hackaboss.agenciaTurismo.model.Hotel;
import com.hackaboss.agenciaTurismo.model.User;
import com.hackaboss.agenciaTurismo.repository.BookHotelRepository;
import com.hackaboss.agenciaTurismo.repository.HotelRepository;
import com.hackaboss.agenciaTurismo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookHotelService implements IBookHotelService{

    @Autowired
    private BookHotelRepository bookHotelRep;
    @Autowired
    private HotelRepository hotelRep;
    @Autowired
    private UserRepository userRep;
    @Autowired
    private IUserService userServ;


    @Override
    public BookHotel createBookHotel(BookHotelDto bookHotelDto) {
        // Inicializamos BookHotel y obtenemos el hotel existente
        BookHotel bookHotel = new BookHotel();
        Hotel hotelExist = hotelRep.findByHotelCode(bookHotelDto.getHotelCode());

        if (hotelExist == null) {
            throw new IllegalArgumentException("The hotel does not exist");
        }

        //Obtenemos los datos de hoteles
        List<Hotel> hotelList = hotelRep.findAll();

        //Verificamos la fecha
        LocalDate dateFrom = bookHotelDto.getDateFrom();
        LocalDate dateTo = bookHotelDto.getDateTo();

        //Obtenemos las fechas de disponibilidad del hotel
        LocalDate startDate = hotelExist.getDisponibilityDateFrom();
        LocalDate endDate = hotelExist.getDisponibilityDateTo();

        //Verificamos las fechas de reserva con las fechas de disponibilidad del hotel
        if (dateTo.isBefore(startDate) || dateFrom.isAfter(endDate)) {
            throw new IllegalArgumentException("The reservation dates are not within the hotel's availability range or are invalid.");
        } else {
            //Verificamos si el hotel no está reservado y seteamos los datos
            if (!hotelExist.isBooked()) {
                hotelRep.save(hotelExist);

                bookHotel.setHotelCode(hotelExist.getHotelCode());
                bookHotel.setDateFrom(dateFrom);
                bookHotel.setDateTo(dateTo);

                //Calculamos el numero de noches
                int nights = (int) ChronoUnit.DAYS.between(dateFrom, dateTo);
                bookHotel.setNights(nights);

                //Creamos la lista de usuarios, seteamos los datos y lo guardamos
                List<User> hosts = new ArrayList<>();

                for (User userDto : bookHotelDto.getHosts()) {
                    User host = userServ.findByPassport(userDto.getPassport());
                    if (host == null) {
                        host = new User();
                    }

                    host.setName(userDto.getName());
                    host.setLastName(userDto.getLastName());
                    host.setPassport(userDto.getPassport());
                    host.setAge(userDto.getAge());

                    userServ.createUser(host);

                    hosts.add(host);
                }
                //Asociamos el hotel y los usuarios
                bookHotel.setRoomType(bookHotelDto.getRoomType());
                bookHotel.setHotel(hotelExist);
                bookHotel.setHosts(hosts);
                bookHotel.setPeopleQ(hosts.size());

                //Calculamos precio total de la reserva
                Double totalPrice = hotelList.stream()
                        .filter(hotel -> hotel.getHotelCode().equals(bookHotelDto.getHotelCode()) &&
                        hotel.getRoomType().equalsIgnoreCase(bookHotelDto.getRoomType()))
                        .mapToDouble(hotel -> nights * hotel.getRoomPrice())
                        .findFirst()
                        .orElse(0.0);
                bookHotel.setPrice(totalPrice);

                //Comprobamos que el usuario no pueda reservar dos habitaciones distintas con la misma fecha
                List<BookHotel> existingBookings = bookHotelRep.getUserAndDateRange(hosts, dateFrom, dateTo);

                if (!existingBookings.isEmpty()) {
                    throw new IllegalArgumentException("The user has a reservation for the specified dates.");
                }else {
                    //Cambiamos estado de la reserva exitosa
                    hotelExist.setBooked(true);

                    return bookHotelRep.save(bookHotel);
                }
            } else {
                throw new IllegalArgumentException("The hotel is booked");
            }
        }

    }

    @Override
    public List<BookHotel> getAllBookHotels() {
        List<BookHotel> bookHotels = bookHotelRep.findAll();

        if (!bookHotels.isEmpty()){
            return bookHotels;
        }else{
            throw new IllegalArgumentException("There are no booked hotels.");
        }
    }

    @Override
    public BookHotel getBookHotelById(Long id) {
        //Comprobamos que la reserva exista
        Optional<BookHotel> optionalBookHotel = bookHotelRep.findById(id);
        if (optionalBookHotel.isPresent()) {
            BookHotel bookHotel = optionalBookHotel.get();
            return bookHotel;
        }else{
            throw new IllegalArgumentException("The hotel reservation does not exist");
        }
    }

    @Override
    public BookHotel deleteBookHotelById(Long id) {
        //Comprobamos que la reserva exista
        Optional<BookHotel> optionalBookHotel = bookHotelRep.findById(id);
        if (optionalBookHotel.isPresent()) {
            BookHotel bookhotel = optionalBookHotel.get();
            bookHotelRep.deleteById(id);
            return bookhotel;
        }else{
            throw new IllegalArgumentException("The hotel reservation does not exist");
        }
    }

    @Override
    public BookHotel editBookHotel(Long id, BookHotelDto bookHotelDto) {
        Optional<BookHotel> optionalBookHotel = bookHotelRep.findById(id);

        if (optionalBookHotel.isPresent()) {
            BookHotel existingBooking = optionalBookHotel.get();
            Hotel hotelExist = hotelRep.findByHotelCode(bookHotelDto.getHotelCode());

            //Realizamos las validaciones específicas para la edición
            if (hotelExist == null) {
                throw new IllegalArgumentException("The hotel does not exist");
            }

            LocalDate dateFrom = bookHotelDto.getDateFrom();
            LocalDate dateTo = bookHotelDto.getDateTo();
            int nights = (int) ChronoUnit.DAYS.between(dateFrom, dateTo);

            if (dateTo.isBefore(hotelExist.getDisponibilityDateFrom()) || dateFrom.isAfter(hotelExist.getDisponibilityDateTo())) {
                throw new IllegalArgumentException("The reservation dates are not within the hotel's availability range or are invalid.");
            }

            //Actualizamos los datos de la reserva existente con los nuevos datos proporcionados en bookHotelDto
            existingBooking.setHotelCode(hotelExist.getHotelCode());
            existingBooking.setDateFrom(dateFrom);
            existingBooking.setDateTo(dateTo);
            existingBooking.setNights(nights);

            List<User> updatedHosts = new ArrayList<>();

            for (User userDto : bookHotelDto.getHosts()) {
                User host = userServ.findByPassport(userDto.getPassport());

                if (host == null) {
                    host = new User();
                }

                host.setName(userDto.getName());
                host.setLastName(userDto.getLastName());
                host.setPassport(userDto.getPassport());
                host.setAge(userDto.getAge());

                userServ.createUser(host);

                updatedHosts.add(host);
            }

            existingBooking.setHosts(updatedHosts);
            existingBooking.setPeopleQ(updatedHosts.size());
            existingBooking.setRoomType(bookHotelDto.getRoomType());
            existingBooking.setHotel(hotelExist);

            Double totalPrice = hotelRep.findAll().stream()
                    .filter(hotel -> hotel.getHotelCode().equals(bookHotelDto.getHotelCode()) &&
                            hotel.getRoomType().equalsIgnoreCase(bookHotelDto.getRoomType()))
                    .mapToDouble(hotel -> nights * hotel.getRoomPrice())
                    .findFirst()
                    .orElse(0.0);
            existingBooking.setPrice(totalPrice);


            return bookHotelRep.save(existingBooking);
        } else {
            throw new IllegalArgumentException("The hotel reservation does not exist");
        }
    }
}
