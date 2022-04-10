package com.lukomski.wojtek.LibraryOnlineApp.model;

import lombok.Data;

@Data
public class ReturnBookRequest {
    private final boolean available;
    private final Integer userId;
    private final Integer bookId;

}

