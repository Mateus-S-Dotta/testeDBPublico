package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Rent;
import com.example.demo.models.User;
import com.example.demo.repositories.RentRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private UserRepository userRepository;

    public Rent findById(Long id) {
        Optional<Rent> rent = this.rentRepository.findById(id);
        return rent.orElseThrow(() -> new RuntimeException("Rent not found with id: " + id));
    }

    @Transactional
    public Rent create(Rent obj) {
        obj.setId(null);
        if (obj.getUser() == null || obj.getRetreat() == null || obj.getDevolution() == null) {
            throw new IllegalArgumentException("User, Retreat, and Devolution must not be null");
        }

        Optional<User> user = this.userRepository.findById(obj.getUser().getId());
        if (!user.isPresent()) {
            throw new IllegalArgumentException("User does not exist");
        }
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
