package com.lukomski.wojtek.LibraryOnlineApp.model;

import lombok.Data;
@Data
public class ReturnBookResponse {
    private final Book book;
    private final User user;
    private final String message = "Thank You for returning book on time!";

}

