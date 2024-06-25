package com.example.demo.models;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = Book.TABLE_NAME)
public class Book {
    public static final String TABLE_NAME = "book";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @NotBlank
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private String name;

    @Column(name = "publish", nullable = false)
    @NotNull
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private LocalDate publish;

    @Column(name = "isbn", unique = true, nullable = false)
    @NotBlank
    @Size(min = 10, max = 13)
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private String isbn;

    @ManyToMany
    @JoinTable(name = "rent_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "rent_id"))
    @JsonView(Views.BookView.class)
    private Set<Rent> rents;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "author_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    @JsonView(Views.BookView.class)
    @NotNull
    private Set<Author> authors;

    public Book() {
    }

    public Book(Long id, String name, LocalDate publish, String isbn, Set<Rent> rents) {
        this.id = id;
        this.name = name;
        this.publish = publish;
        this.isbn = isbn;
        this.rents = rents;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPublish() {
        return this.publish;
    }

    public void setPublish(LocalDate publish) {
        this.publish = publish;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Set<Rent> getRents() {
        return this.rents;
    }

    public void setRents(Set<Rent> rents) {
        this.rents = rents;
    }

    public Set<Author> getAuthors() {
        return this.authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Book))
            return false;
        Book other = (Book) o;
        if (this.id == null)
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id))
                return false;
        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name)
                && Objects.equals(this.publish, other.publish) && Objects.equals(this.isbn, other.isbn)
                && Objects.equals(this.authors, other.authors) && Objects.equals(this.rents, other.rents);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
