package com.example.demo.controllers;

import java.net.URI;
import java.util.List;
import java.util.Set;

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

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.models.Views;
import com.example.demo.services.AuthorService;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/author")
@Validated
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/{id}")
    @JsonView(Views.AuthorView.class)
    public ResponseEntity<Author> findById(@PathVariable Long id) {
        Author obj = this.authorService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/{id}/books")
    @JsonView(Views.AuthorView.class)
    public ResponseEntity<Set<Book>> findByAuthorBooksId(@PathVariable Long id) {
        Author obj = this.authorService.findById(id);
        Set<Book> books = obj.getBooks();
        return ResponseEntity.ok().body(books);
    }

    @GetMapping
    @JsonView(Views.AuthorView.class)
    public ResponseEntity<List<Author>> listAll(@RequestParam(required = false) String name) {
        if (name != null) {
            List<Author> obj = this.authorService.findByName(name);
            return ResponseEntity.ok().body(obj);
        }
        List<Author> authors = this.authorService.findAll();
        return ResponseEntity.ok().body(authors);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Author obj) {
        System.out.println(obj.getBooks());
        this.authorService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Author obj, @PathVariable Long id) {
        obj.setId(id);
        this.authorService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
