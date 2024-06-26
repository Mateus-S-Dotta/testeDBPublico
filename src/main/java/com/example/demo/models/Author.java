package com.example.demo.models;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = Author.TABLE_NAME)
public class Author {
    public static final String TABLE_NAME = "author";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @NotBlank
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private String name;

    @Column(name = "gender")
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private Boolean gender;

    @Column(name = "birth")
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private LocalDate birth;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    @NotBlank
    @Size(min = 11, max = 11)
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private String cpf;

    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonView(Views.AuthorView.class)
    private Set<Book> books;

    @PreRemove
    private void preventDeleteIfHasBooks() {
        if (!this.books.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir um autor que possui livros associados.");
        }
    }

    public Author() {
    }

    public Author(Long id, String name, Boolean gender, LocalDate birth, String cpf, Set<Book> books) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.cpf = cpf;
        this.books = books;
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

    public Boolean getgender() {
        return this.gender;
    }

    public void setgender(Boolean gender) {
        this.gender = gender;
    }

    public LocalDate getBirth() {
        return this.birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof Author))
            return false;
        Author other = (Author) o;
        if (this.id == null)
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id))
                return false;
        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name)
                && Objects.equals(this.gender, other.gender) && Objects.equals(this.birth, other.birth)
                && Objects.equals(this.cpf, other.cpf) && Objects.equals(this.books, other.books);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
