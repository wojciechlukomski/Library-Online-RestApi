package com.lukomski.wojtek.LibraryOnlineApp.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseBookRequest {
    private final Integer userId;
    private final Integer bookId;

}