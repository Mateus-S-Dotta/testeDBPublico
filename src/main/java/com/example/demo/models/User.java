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
@Table(name = User.TABLE_NAME)
public class User {
    public interface CreateUser {
    }

    public interface UpdateUser {
    }

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    @NotBlank(groups = { CreateUser.class, UpdateUser.class })
    private String name;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    @NotBlank(groups = { CreateUser.class, UpdateUser.class })
    @Size(groups = { CreateUser.class, UpdateUser.class }, min = 11, max = 11)
    private String cpf;

    @Column(name = "phone", length = 11, nullable = false)
    @NotBlank(groups = { CreateUser.class, UpdateUser.class })
    @Size(groups = { CreateUser.class, UpdateUser.class }, min = 11, max = 11)
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(groups = { CreateUser.class, UpdateUser.class })
    private String email;

    @Column(name = "birth")
    private LocalDate birth;

    public User() {
    }

    public User(Long id, String name, Boolean gender, String cpf, String phone, String email, LocalDate birth) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.cpf = cpf;
        this.phone = phone;
        this.email = email;
        this.birth = birth;
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

    public Boolean isGender() {
        return this.gender;
    }

    public Boolean getGender() {
        return this.gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirth() {
        return this.birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof User))
            return false;
        User other = (User) o;
        if (this.id == null)
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id))
                return false;
        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name)
                && Objects.equals(this.gender, other.gender) && Objects.equals(this.birth, other.birth)
                && Objects.equals(this.cpf, other.cpf) && Objects.equals(this.phone, other.phone)
                && Objects.equals(this.email, other.email);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
}
