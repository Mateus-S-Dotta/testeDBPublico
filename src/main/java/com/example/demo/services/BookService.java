package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book findById(Long id) {
        Optional<Book> book = this.bookRepository.findById(id);
        return book.orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
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

    public void delete(Long id) {
        findById(id);
        try {
            this.bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting book");
        }
    }
}
