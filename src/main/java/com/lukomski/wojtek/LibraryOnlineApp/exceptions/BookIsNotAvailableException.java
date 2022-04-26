package com.lukomski.wojtek.LibraryOnlineApp.exceptions;

public class BookIsNotAvailableException extends RuntimeException {
    public BookIsNotAvailableException(String message) {
        super(message);
    }
}
