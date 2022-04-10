package com.lukomski.wojtek.LibraryOnlineApp.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "users")
@Data
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

    @OneToOne
    @JoinColumn(referencedColumnName = "bookId", name = "borrowedBook")
    private Book borrowedBook;

    @OneToOne
    @JoinColumn(referencedColumnName = "bookId", name = "purchasedBook")
    private Book purchasedBook;


}

