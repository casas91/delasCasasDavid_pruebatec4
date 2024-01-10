package com.hackaboss.agenciaTurismo.repository;

import com.hackaboss.agenciaTurismo.model.BookFlight;
import com.hackaboss.agenciaTurismo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookFlightRepository extends JpaRepository<BookFlight, Long> {
    @Query("SELECT bf FROM BookFlight bf " +
            "INNER JOIN bf.passengers p " +
            "WHERE p IN :passengers " +
            "AND bf.date = :date")
    List<BookFlight> getUserExistingBookings(@Param("passengers") List<User> passengers,
                                           @Param("date") LocalDate date);

}
