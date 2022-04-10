package com.lukomski.wojtek.LibraryOnlineApp.services;

import com.lukomski.wojtek.LibraryOnlineApp.exceptions.RentTimeTooLongException;
import com.lukomski.wojtek.LibraryOnlineApp.model.*;
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

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Book add(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Integer bookId) {
        bookRepository.deleteById(bookId);
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

    public List<Book> getAllByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }
    public List<Book> getDistinct(){
        return bookRepository.findDistinctByTitle();  // rzuca exception !
    }

    public RentBookResponse rentBook(boolean available, LocalDate dateOfStartRent, LocalDate dateOfEndRent, Integer bookId, Integer userId) {
        long daysOfRent = ChronoUnit.DAYS.between(dateOfStartRent, dateOfEndRent);
        if (daysOfRent > 50) {
            throw new RentTimeTooLongException("Rent time is limited to 50 days, please choose shorter period");
        }
        Book book = bookRepository.getById(bookId);
        User userRentingBook = userRepository.getById(userId);
        userRentingBook.setBorrowedBook(book);
        userRepository.save(userRentingBook);
        bookRepository.updateDateOfRentWithAvailability(available, dateOfStartRent, dateOfEndRent, bookId);
        BigDecimal price = book.getPricePerDay().multiply(new BigDecimal(daysOfRent));
        return new RentBookResponse(book,userRentingBook,price);
    }
    public ReturnBookResponse returnBook(boolean available, Integer bookId, Integer userId){
        Book book = bookRepository.getById(bookId);
        User userRentingBook = userRepository.getById(userId);
        userRentingBook.setBorrowedBook(null);
        userRepository.save(userRentingBook);
        bookRepository.updateAvailability(available, bookId);
        book.setDateOfEndRent(null);
        book.setDateOfStartRent(null);
        book.setAvailable(true);
        bookRepository.save(book);

        return new ReturnBookResponse(book,userRentingBook);
    }
    public PurchaseBookResponse purchaseBook(Integer bookId, Integer userId){
        Book book = bookRepository.getById(bookId);
        User userRentingBook = userRepository.getById(userId);
        userRentingBook.setPurchasedBook(book);
        userRepository.save(userRentingBook);
        bookRepository.deleteById(bookId);
        BigDecimal retailPrice = book.getRetailPrice();

        return new PurchaseBookResponse(book,userRentingBook,retailPrice);
    }
}
