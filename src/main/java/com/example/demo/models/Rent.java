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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = Rent.TABLE_NAME)
public class Rent {
    public static final String TABLE_NAME = "rent";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonView(Views.RentView.class)
    @NotNull
    private User user;

    @Column(name = "retreat", nullable = false)
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private LocalDate retreat = LocalDate.now();

    @Column(name = "devolution", nullable = false)
    @JsonView({ Views.BookView.class, Views.RentView.class, Views.UserView.class, Views.AuthorView.class })
    private LocalDate devolution;

    @ManyToMany
    @JoinTable(name = "rent_book", joinColumns = @JoinColumn(name = "rent_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    @JsonView(Views.RentView.class)
    @NotNull
    private Set<Book> books;

    public Rent() {
    }

    public Rent(Long id, User user, LocalDate retreat, LocalDate devolution, Set<Book> books) {
        this.id = id;
        this.user = user;
        this.retreat = retreat;
        this.devolution = devolution;
        this.books = books;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getRetreat() {
        return this.retreat;
    }

    public void setRetreat(LocalDate retreat) {
        this.retreat = retreat;
    }

    public LocalDate getDevolution() {
        return this.devolution;
    }

    public void setDevolution(LocalDate devolution) {
        this.devolution = devolution;
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
        if (!(o instanceof Rent))
            return false;
        Rent other = (Rent) o;
        if (this.id == null)
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id))
                return false;
        return Objects.equals(this.id, other.id) && Objects.equals(this.user, other.user)
                && Objects.equals(this.retreat, other.retreat) && Objects.equals(this.devolution, other.devolution);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
