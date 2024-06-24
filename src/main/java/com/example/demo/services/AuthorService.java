package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Author;
import com.example.demo.repositories.AuthorRepository;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author findById(Long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        return author.orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    @Transactional
    public Author create(Author obj) {
        obj.setId(null);
        obj = this.authorRepository.save(obj);
        return obj;
    }

    @Transactional
    public Author update(Author obj) {
        if (obj.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update operation");
        }
        return this.authorRepository.save(obj);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            this.authorRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting author with ID" + id);
        }
    }

    @Transactional
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional
    public List<Author> findByName(String name) {
        return authorRepository.findByName(name);
    }
}
