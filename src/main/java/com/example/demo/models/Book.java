package com.example.demo.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = Book.TABLE_NAME)
public class Book {
    public interface CreateBook {
    }

    public interface UpdateBook {
    }

    public static final String TABLE_NAME = "book";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @NotBlank(groups = { CreateBook.class, UpdateBook.class })
    private String name;

    @Column(name = "publish", nullable = false)
    @NotNull(groups = { CreateBook.class, UpdateBook.class })
    private LocalDate publish;

    @Column(name = "isbn", nullable = false)
    @NotBlank(groups = { CreateBook.class, UpdateBook.class })
    @Size(min = 10, max = 13)
    private String isbn;

    public Book() {
    }

    public Book(Long id, String name, LocalDate publish, String isbn) {
        this.id = id;
        this.name = name;
        this.publish = publish;
        this.isbn = isbn;
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

    public Book id(Long id) {
        setId(id);
        return this;
    }

    public Book name(String name) {
        setName(name);
        return this;
    }

    public Book publish(LocalDate publish) {
        setPublish(publish);
        return this;
    }

    public Book isbn(String isbn) {
        setIsbn(isbn);
        return this;
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
                && Objects.equals(this.publish, other.publish) && Objects.equals(this.isbn, other.isbn);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
