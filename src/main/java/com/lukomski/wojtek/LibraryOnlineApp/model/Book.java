package com.lukomski.wojtek.LibraryOnlineApp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity //pojedyncza encja w bazie danych
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //tworzy incrementacje
    private Integer bookId;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String genre;

    @Column
    private BigDecimal pricePerDay;

    @Column
    private BigDecimal retailPrice;

    @Column
    private LocalDate dateOfStartRent;

    @Column
    private LocalDate dateOfEndRent;

    @Column
    private Boolean available = true;
}

