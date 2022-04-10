package com.lukomski.wojtek.LibraryOnlineApp.controller;

import com.lukomski.wojtek.LibraryOnlineApp.exceptions.RentTimeTooLongException;
import com.lukomski.wojtek.LibraryOnlineApp.model.*;
import com.lukomski.wojtek.LibraryOnlineApp.repositories.BookRepository;
import com.lukomski.wojtek.LibraryOnlineApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @GetMapping("book/all")
    List<Book> allBooks() {
        return bookService.getAll();
    }

    @GetMapping("book/genre/{genre}")
    ResponseEntity<List<Book>> booksByGenre(@PathVariable String genre) {
        Optional<List<Book>> book1 = Optional.of(bookService.getAllByGenre(genre));
        return book1
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("book/titles")
    List<Book> distinctBooks() {
        return bookService.getDistinct();
    }

    @GetMapping("book/available")
    List<Book> availableBooks() {
        return bookService.getAvailable();
    }

    @GetMapping(value = "book/title/{title}")
    ResponseEntity<Book> specificBookByTitle(@PathVariable String title) { //wciele odpowiedzi bedzie Book
        Optional<Book> book1 = Optional.of(bookService.getByTitle(title));
        return book1
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/book/delete/{id}")
    ResponseEntity<Integer> deleteBook(@PathVariable Integer id) {
        bookService.delete(id);
        return new ResponseEntity<Integer>(id, HttpStatus.OK);
    }


    @PostMapping(value = "/book/add", consumes = "application/json")
    ResponseEntity<Book> addBook(@RequestBody Book book) { // zwraca nam status czyli Response Entity
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

    @PostMapping(value = "/book/return", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReturnBookResponse> returnBook(@RequestBody ReturnBookRequest returnBookRequest) {
        Optional<ReturnBookResponse> returnBookResponse = Optional.of(bookService.returnBook(returnBookRequest.isAvailable(),
                returnBookRequest.getBookId(), returnBookRequest.getUserId()));
        return ResponseEntity.of(returnBookResponse);


    }

    @PutMapping(value = "/book/purchase", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PurchaseBookResponse> purchaseBook(@RequestBody PurchaseBookRequest purchaseBookRequest) {
        Optional<PurchaseBookResponse> purchaseBookResponse = Optional.of(bookService.purchaseBook(purchaseBookRequest.getBookId()
                ,purchaseBookRequest.getUserId()));
        return ResponseEntity.of(purchaseBookResponse);
    }
}
