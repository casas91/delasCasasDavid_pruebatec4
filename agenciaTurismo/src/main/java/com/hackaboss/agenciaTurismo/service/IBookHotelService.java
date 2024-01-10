package com.hackaboss.agenciaTurismo.service;

import com.hackaboss.agenciaTurismo.dto.BookHotelDto;
import com.hackaboss.agenciaTurismo.model.BookHotel;

import java.util.List;

public interface IBookHotelService {

    BookHotel createBookHotel(BookHotelDto bookHotelDto);

    List<BookHotel> getAllBookHotels();

    BookHotel getBookHotelById(Long id);

    BookHotel deleteBookHotelById(Long id);

    BookHotel editBookHotel(Long id, BookHotelDto bookHotelDto);

}
