package com.lukomski.wojtek.LibraryOnlineApp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
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


    @OneToOne
    @JoinColumn(name = "user_renting_id")
    private User userRenting;

    @OneToOne
    @JoinColumn(name = "user_purchasing_id")
    private User userPurchasing;


}



