package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT a FROM Book a WHERE a.name LIKE %:name%")
    List<Book> findByName(@Param("name") String name);

    @Query("SELECT b FROM Rent r JOIN r.books b WHERE r.devolution >= CURRENT_DATE")
    List<Book> findBooksByDevolutionAfter();

    @Query("SELECT DISTINCT b FROM Book b WHERE b NOT IN (SELECT b FROM Book b JOIN b.rents r WHERE r.devolution >= CURRENT_DATE)")
    List<Book> findAvailableBooks();
}
