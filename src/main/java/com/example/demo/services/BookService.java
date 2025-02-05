package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Book;
import com.example.demo.models.Rent;
import com.example.demo.repositories.BookRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book findById(Long id) {
        Optional<Book> book = this.bookRepository.findById(id);
        return book.orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Transactional
    public Book create(Book obj) {
        obj.setId(null);
        obj = this.bookRepository.save(obj);
        return obj;
    }

    @Transactional
    public Book update(Book obj) {
        if (obj.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update operation");
        }
        return this.bookRepository.save(obj);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<Book> optionalBook = this.bookRepository.findById(id);
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                Set<Rent> rents = book.getRents();
                if (rents.isEmpty()) {
                    this.bookRepository.deleteById(id);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting book");
        }
    }

    @Transactional
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public List<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    @Transactional
    public List<Book> findAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }

    @Transactional
    public List<Book> findBooksByDevolutionAfter() {
        return bookRepository.findBooksByDevolutionAfter();
    }

    @Transactional
    public List<Book> findDistinctBooksByUserId(Long userId) {
        return bookRepository.findDistinctBooksByUserId(userId);
    }
}
