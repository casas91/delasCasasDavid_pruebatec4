package com.hackaboss.agenciaTurismo.repository;

import com.hackaboss.agenciaTurismo.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Hotel findByHotelCode(String hotelCode);

    //Si existe alguna fecha disponible entre las fechas introducidas devolverá el hotel
    // aunque alguna de las fechas introducidas esté fuera del rango.
    @Query("SELECT h FROM Hotel h WHERE h.disponibilityDateFrom <= :disponibilityDateTo " +
            "AND h.disponibilityDateTo >= :disponibilityDateFrom " +
            "AND h.place = :place AND h.isBooked = :isBooked")
    List<Hotel> getHotelsByDatesAndPlace(
            @Param("disponibilityDateFrom") LocalDate disponibilityDateFrom,
            @Param("disponibilityDateTo") LocalDate disponibilityDateTo,
            @Param("place") String place,
            @Param("isBooked") Boolean isBooked);
}
