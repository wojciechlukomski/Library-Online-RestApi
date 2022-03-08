package com.lukomski.wojtek.LibraryOnlineApp.services;

import com.lukomski.wojtek.LibraryOnlineApp.exceptions.RentTimeTooLongException;
import com.lukomski.wojtek.LibraryOnlineApp.model.Book;
import com.lukomski.wojtek.LibraryOnlineApp.model.RentBookResponse;
import com.lukomski.wojtek.LibraryOnlineApp.model.User;
import com.lukomski.wojtek.LibraryOnlineApp.repositories.BookRepository;
import com.lukomski.wojtek.LibraryOnlineApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Book add(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public List<Book> getAvailable() {
        return bookRepository.findAllByAvailable(true);
    }
    public Book getByTitle(String title) {
        return bookRepository.findByTitle(title);
    }
//    public List<Book> getDistinct(){
//        return bookRepository.findDistinctByTitle();  // rzuca exception !
//    }

    public RentBookResponse rentBook(boolean available, LocalDate dateOfStartRent, LocalDate dateOfEndRent, Integer carId, Integer userId) {
        long daysOfRent = ChronoUnit.DAYS.between(dateOfStartRent, dateOfEndRent);
        if (daysOfRent > 50) {
            throw new RentTimeTooLongException("Rent time is limited to 50 days, please choose shorter period");
        }
        Book book = bookRepository.getById(carId);
        User userRentingBook = userRepository.getById(userId);
        userRentingBook.setBook(book);
        userRepository.save(userRentingBook);
        bookRepository.updateDateOfRentWithAvailbility(available, dateOfStartRent, dateOfEndRent, carId);
        BigDecimal price = book.getPricePerDay().multiply(new BigDecimal(daysOfRent));
        return new RentBookResponse(book,userRentingBook,price);
    }
}
