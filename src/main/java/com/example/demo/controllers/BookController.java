package com.example.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.models.Book;
import com.example.demo.models.Views;
import com.example.demo.services.BookService;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
@Validated
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    @JsonView(Views.BookView.class)
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        Book obj = this.bookService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/findByUser/{id}")
    @JsonView(Views.BookView.class)
    public ResponseEntity<List<Book>> findDistinctBooksByUserId(@PathVariable Long id) {
        List<Book> obj = this.bookService.findDistinctBooksByUserId(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    @JsonView(Views.BookView.class)
    public ResponseEntity<List<Book>> listAll(@RequestParam(required = false) String name,
            @RequestParam(required = false) String active) {
        if (name != null) {
            List<Book> obj = this.bookService.findByName(name);
            return ResponseEntity.ok().body(obj);
        } else if ("true".equals(active)) {
            System.out.println("true");
            List<Book> books = this.bookService.findAvailableBooks();
            return ResponseEntity.ok().body(books);
        } else if ("false".equals(active)) {
            System.out.println("false");
            List<Book> books = this.bookService.findBooksByDevolutionAfter();
            return ResponseEntity.ok().body(books);
        }
        List<Book> books = this.bookService.findAll();
        return ResponseEntity.ok().body(books);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Book obj) {
        System.out.println(obj.getAuthors());
        this.bookService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Book obj, @PathVariable Long id) {
        Book book = this.bookService.findById(id);
        obj.setRents(book.getRents());
        obj.setId(id);
        this.bookService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
