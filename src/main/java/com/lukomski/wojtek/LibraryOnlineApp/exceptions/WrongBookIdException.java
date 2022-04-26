package com.lukomski.wojtek.LibraryOnlineApp.exceptions;

public class WrongBookIdException extends RuntimeException {
    public WrongBookIdException(String message) {
        super(message);
    }
}
