package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Rent;
import com.example.demo.repositories.RentRepository;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;

    public Rent findById(Long id) {
        Optional<Rent> rent = this.rentRepository.findById(id);
        return rent.orElseThrow(() -> new RuntimeException("Rent not found with id: " + id));
    }

    @Transactional
    public Rent create(Rent obj) {
        obj.setId(null);
        obj = this.rentRepository.save(obj);
        return obj;
    }

    @Transactional
    public Rent update(Rent obj) {
        if (obj.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update operation");
        }
        return this.rentRepository.save(obj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.rentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting rent with ID" + id);
        }
    }
}
