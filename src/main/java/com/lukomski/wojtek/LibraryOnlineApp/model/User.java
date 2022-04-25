package com.lukomski.wojtek.LibraryOnlineApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JoinColumn
    @OneToOne
    private Book borrowedBook;

    @JoinColumn
    @OneToOne
    private Book purchasedBook;

//    @OneToMany(mappedBy = "userRenting",fetch = FetchType.LAZY)
//    private List<Book> borrowedBooks;


//    private List<Book> purchasedBook = new ArrayList<>();

//    public void saveBooks(Book book) {
//        borrowedBook.add(book);
//    }
}

