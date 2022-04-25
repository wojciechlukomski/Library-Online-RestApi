package com.lukomski.wojtek.LibraryOnlineApp.services;

import com.lukomski.wojtek.LibraryOnlineApp.exceptions.BookIsNotAvailableException;
import com.lukomski.wojtek.LibraryOnlineApp.exceptions.RentTimeTooLongException;
import com.lukomski.wojtek.LibraryOnlineApp.model.*;
import com.lukomski.wojtek.LibraryOnlineApp.repositories.BookRepository;
import com.lukomski.wojtek.LibraryOnlineApp.repositories.UserRepository;
import com.lukomski.wojtek.LibraryOnlineApp.validators.BookAvailableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookAvailableValidator bookAvailableValidator;
    public static List<Book> purchasedBooks = new LinkedList<>();

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository, BookAvailableValidator bookAvailableValidator) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookAvailableValidator = bookAvailableValidator;
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
        if(!bookAvailableValidator.validate(book)) {
            throw new BookIsNotAvailableException("This book is not available! Please choose different book");
        }
            User userRentingBook = userRepository.getById(userId);
            userRentingBook.setBorrowedBook(book);
            book.setUserRenting(userRentingBook);


            userRepository.save(userRentingBook);
            bookRepository.updateDateOfRentWithAvailability(available, dateOfStartRent, dateOfEndRent, bookId);
            BigDecimal price = book.getPricePerDay().multiply(new BigDecimal(daysOfRent));
            bookRepository.save(book);
            return new RentBookResponse(book,userRentingBook, price);
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
        if(!bookAvailableValidator.validate(book)) {
            throw new BookIsNotAvailableException("This book is not available! Please choose different book");
        }
        User userPurchasingBook = userRepository.getById(userId);

        userPurchasingBook.setPurchasedBook(book);
        userRepository.save(userPurchasingBook);
        book.setUserPurchasing(userPurchasingBook);
        book.setAvailable(false);
        bookRepository.save(book);
        BigDecimal retailPrice = book.getRetailPrice();

        return new PurchaseBookResponse(book,userPurchasingBook,retailPrice);
    }
}
