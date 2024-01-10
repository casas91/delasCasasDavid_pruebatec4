package com.hackaboss.agenciaTurismo.repository;

import com.hackaboss.agenciaTurismo.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT DISTINCT fli " +
            "FROM Flight fli " +
            "WHERE fli.date BETWEEN :date1 AND :date2 " +
            "AND fli.origin = :origin " +
            "AND fli.destination = :destination")
    List<Flight> getAllFlightsByPlaceAndDate(
            @Param("date1") LocalDate date1,
            @Param("date2") LocalDate date2,
            @Param("origin") String origin,
            @Param("destination") String destination);


    Flight findByFlightNumberAndOriginAndSeatType(String flightCode, String origin, String seatType);
}