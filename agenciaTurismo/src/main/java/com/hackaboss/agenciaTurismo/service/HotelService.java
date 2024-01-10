package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.dto.HotelDto;
import com.hackaboss.agenciaTurismo.model.Hotel;
import com.hackaboss.agenciaTurismo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements IHotelService{

    @Autowired
    private HotelRepository hotelRep;

    @Override
    public Hotel createHotel(HotelDto hotelDto) {
        Hotel hotel = new Hotel();

        hotel.setHotelCode(hotelDto.getHotelCode());
        hotel.setName(hotelDto.getName());
        hotel.setPlace(hotelDto.getPlace());
        hotel.setBooked(false);
        hotel.setRoomType(hotelDto.getRoomType());
        hotel.setDisponibilityDateFrom(hotelDto.getDisponibilityDateFrom());
        hotel.setDisponibilityDateTo(hotelDto.getDisponibilityDateTo());
        hotel.setRoomPrice(hotelDto.getRoomPrice());

        return hotelRep.save(hotel);
    }

    @Override
    public List<Hotel> getHotelsByDatesAndPlace(LocalDate disponibilityDateFrom, LocalDate disponibilityDateTo, String place, Boolean isBooked) {
        return hotelRep.getHotelsByDatesAndPlace(disponibilityDateFrom, disponibilityDateTo, place, isBooked);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRep.findAll();
    }

    @Override
    public Hotel deleteHotelById(Long id) {
        Optional<Hotel> optionalHotel = hotelRep.findById(id);

        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();

            if (!hotel.getBookHotels().isEmpty()) {
                //Si el hotel tiene reservas, no se puede eliminar
                return null;
            }

            hotelRep.deleteById(id);
            return hotel;
        }

        return null;
    }

    @Override
    public Hotel editHotelById(Long id, HotelDto updatedHotelDto) {
        //Verificamos si el hotel existe
        Hotel existingHotel = hotelRep.findById(id).orElse(null);

        if (existingHotel != null) {
            existingHotel.setHotelCode(updatedHotelDto.getHotelCode());
            existingHotel.setName(updatedHotelDto.getName());
            existingHotel.setPlace(updatedHotelDto.getPlace());
            existingHotel.setRoomType(updatedHotelDto.getRoomType());
            existingHotel.setRoomPrice(updatedHotelDto.getRoomPrice());
            existingHotel.setDisponibilityDateFrom(updatedHotelDto.getDisponibilityDateFrom());
            existingHotel.setDisponibilityDateTo(updatedHotelDto.getDisponibilityDateTo());

            return hotelRep.save(existingHotel);
        } else {
            return null;
        }
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRep.findById(id).orElse(null);
    }
}
