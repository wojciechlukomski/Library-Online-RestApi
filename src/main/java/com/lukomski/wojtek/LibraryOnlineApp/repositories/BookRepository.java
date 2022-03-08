package com.lukomski.wojtek.LibraryOnlineApp.repositories;

import com.lukomski.wojtek.LibraryOnlineApp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository <Book,Integer> {


    Book findBookByBookId(Integer bookId);

    List<Book> findAllByAvailable(boolean available);

    Book findByTitle(String title);


//    @Query("select distinct b from Book b where b.title = ?1")
//    List<Book> findDistinctByTitle();
//
    @Modifying //rodzaj querki ktore modyfikuje DB
    @Transactional
    //ma sie odbyc w jednej tranzakcji czyli na raz, zrob wszystko co jest w QUERCY a jesli sie nie powiedzie to wroc.(rollBack)
    @Query(value = "UPDATE Book book SET available=?1, dateOfStartRent =?2, dateOfEndRent=?3 WHERE id=?4")
    void updateDateOfRentWithAvailbility(boolean available, LocalDate dateOfStartRent, LocalDate dateOfEndRent, Integer bookId);
}

