package com.example.demo.models;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = Author.TABLE_NAME)
public class Author {
    public interface CreateAuthor {
    }

    public interface UpdateAuthor {
    }

    public static final String TABLE_NAME = "author";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @NotBlank(groups = { CreateAuthor.class, UpdateAuthor.class })
    private String name;

    @Column(name = "sex")
    private Boolean sex;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    @NotBlank(groups = { CreateAuthor.class, UpdateAuthor.class })
    @Size(groups = { CreateAuthor.class, UpdateAuthor.class }, min = 11, max = 11)
    private String cpf;

    public Author() {
    }

    public Author(Long id, String name, Boolean sex, LocalDate birth, String cpf) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.cpf = cpf;
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

    public Boolean isSex() {
        return this.sex;
    }

    public Boolean getSex() {
        return this.sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
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
                && Objects.equals(this.sex, other.sex) && Objects.equals(this.birth, other.birth)
                && Objects.equals(this.cpf, other.cpf);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
