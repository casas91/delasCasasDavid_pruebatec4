package com.hackaboss.agenciaTurismo.repository;

import com.hackaboss.agenciaTurismo.model.BookHotel;
import com.hackaboss.agenciaTurismo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookHotelRepository extends JpaRepository<BookHotel, Long> {
    @Query("SELECT bh FROM BookHotel bh " +
            "INNER JOIN bh.hosts u " +
            "WHERE (bh.dateFrom BETWEEN :dateFrom AND :dateTo OR bh.dateTo BETWEEN :dateFrom AND :dateTo) " +
            "AND u IN :hosts")
    List<BookHotel> getUserAndDateRange(@Param("hosts") List<User> hosts,
                                        @Param("dateFrom") LocalDate dateFrom,
                                        @Param("dateTo") LocalDate dateTo);
}
