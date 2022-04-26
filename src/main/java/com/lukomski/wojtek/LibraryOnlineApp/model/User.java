package com.lukomski.wojtek.LibraryOnlineApp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String email;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userRenting", cascade = CascadeType.ALL)
    private List<Book> borrowedBooks = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userPurchasing", cascade = CascadeType.ALL)
    private List<Book> purchasedBooks = new ArrayList<>();

    public void saveBorrowedBooks(Book book) {
        borrowedBooks.add(book);
    }

    public void savePurchasedBooks(Book book) {
        purchasedBooks.add(book);
    }
    public void returnBorrowedBook(Book book){
        borrowedBooks.remove(book);
    }
}

