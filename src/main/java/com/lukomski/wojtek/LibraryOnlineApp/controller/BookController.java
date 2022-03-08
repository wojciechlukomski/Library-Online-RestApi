package com.lukomski.wojtek.LibraryOnlineApp.controller;

import com.lukomski.wojtek.LibraryOnlineApp.exceptions.RentTimeTooLongException;
import com.lukomski.wojtek.LibraryOnlineApp.model.Book;
import com.lukomski.wojtek.LibraryOnlineApp.model.RentBookRequest;
import com.lukomski.wojtek.LibraryOnlineApp.model.RentBookResponse;
import com.lukomski.wojtek.LibraryOnlineApp.repositories.BookRepository;
import com.lukomski.wojtek.LibraryOnlineApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("book/all")
    List<Book> allBooks() {
        return bookService.getAll();
    }
//
//    @GetMapping("book/titles")
//    List<Book> distinctBooks() {
//        return bookService.getDistinct();
//    }

    @GetMapping("book/available")
    List<Book> availableBooks() {
        return bookService.getAvailable();
    }

    @GetMapping(value = "/book/title/{title}")
    Book specificBookByTitle(@PathVariable String title) {
        return bookService.getByTitle(title);
    }


    @PostMapping(value = "/book/add", consumes = "application/json")
    ResponseEntity<Book> addCar(@RequestBody Book book) { // zwraca nam status czyli Response Entity
        Optional<Book> book1 = Optional.of(bookService.add(book)); // Optional jest po to aby wrapowac funkcje ktore moga zwrocic NULLA
        return ResponseEntity.of(book1);
    }

    @PostMapping(value = "/book/rent", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RentBookResponse> rent(@RequestBody RentBookRequest rentBookRequest) {
        Optional<RentBookResponse> rentBookResponse = Optional.of(bookService.rentBook(rentBookRequest.isAvailable(), rentBookRequest.getDateOfStartRent()
                , rentBookRequest.getDateOfEndRent(), rentBookRequest.getBookId(), rentBookRequest.getUserId()));
        return ResponseEntity.of(rentBookResponse);
    }

    @ExceptionHandler(RentTimeTooLongException.class)
    ResponseEntity<String> handlerExceptions() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rent time is limited to 50 days, please choose shorter period");
    }
}
