package com.lukomski.wojtek.LibraryOnlineApp.exceptions;

public class RentTimeTooLongException extends RuntimeException {
    public RentTimeTooLongException(String message) {
        super(message);
    }
}
