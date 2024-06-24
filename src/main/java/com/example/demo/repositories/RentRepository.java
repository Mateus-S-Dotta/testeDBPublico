package com.example.demo.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Rent;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    @Query("SELECT r FROM Rent r WHERE r.user.id = :userId AND r.devolution > :today")
    List<Rent> findByUserIdAndDevolutionAfter(@Param("userId") Long userId, @Param("today") LocalDate today);
}
