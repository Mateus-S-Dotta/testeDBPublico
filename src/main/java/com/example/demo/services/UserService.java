package com.example.demo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.Rent;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RentService rentService;

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj) {
        if (obj.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update operation");
        }
        return this.userRepository.save(obj);
    }

    @Transactional
    public void delete(Long id) {
        boolean hasActiveRents = rentService.hasActiveRents(id);
        if (!hasActiveRents) {
            try {
                this.userRepository.deleteById(id);
                return;
            } catch (Exception e) {
                throw new RuntimeException("Error deleting user with ID" + id);
            }
        }
        List<Rent> activeRents = rentService.getActiveRents(id);
        LocalDate today = LocalDate.now();
        for (Rent rent : activeRents) {
            if (rent.getDevolution().isAfter(today)) {
                throw new RuntimeException(
                        "O usuário possui aluguéis ativos com devolução no futuro e não pode ser excluído.");
            }
        }
        try {
            this.userRepository.deleteById(id);
            return;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user with ID" + id);
        }
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
