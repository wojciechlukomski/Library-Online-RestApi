package com.lukomski.wojtek.LibraryOnlineApp.validators;

import com.lukomski.wojtek.LibraryOnlineApp.exceptions.BookIsNotAvailableException;
import com.lukomski.wojtek.LibraryOnlineApp.exceptions.WrongBookIdException;
import com.lukomski.wojtek.LibraryOnlineApp.model.Book;
import com.lukomski.wojtek.LibraryOnlineApp.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookAvailableValidator {
    private final BookRepository bookRepository;

    @Autowired
    public BookAvailableValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public boolean validate(Book book) {
        Book getBook = bookRepository.findBookByBookId(book.getBookId());
        Optional<Book> check = Optional.ofNullable(getBook);
        if (check.isPresent()) {
            return getBook.getAvailable();
        } else {
            throw new WrongBookIdException("Wrong BookId! This book doesn't exist! ");
        }

    }
}
