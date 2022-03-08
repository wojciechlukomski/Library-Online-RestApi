package com.lukomski.wojtek.LibraryOnlineApp.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentBookRequest {
    private final boolean available;
    private final LocalDate dateOfStartRent;
    private final LocalDate dateOfEndRent;
    private final Integer userId;
    private final Integer bookId;

}

