package com.lukomski.wojtek.LibraryOnlineApp.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RentBookResponse {
    private final Book book;
    private final User user;
    private final BigDecimal price;
}
