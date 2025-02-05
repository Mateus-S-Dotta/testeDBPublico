package com.example.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.models.Rent;
import com.example.demo.models.Views;
import com.example.demo.services.RentService;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rent")
@Validated
public class RentController {
    @Autowired
    private RentService rentService;

    @GetMapping("/{id}")
    @JsonView(Views.RentView.class)
    public ResponseEntity<Rent> findById(@PathVariable Long id) {
        Rent obj = this.rentService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    @JsonView(Views.RentView.class)
    public ResponseEntity<List<Rent>> listAll(@RequestParam(required = false) String active,
            @RequestParam(required = false) Long userId) {
        if ("true".equals(active)) {
            if (userId == null) {
                List<Rent> rents = this.rentService.findByDevolutionAfter();
                return ResponseEntity.ok().body(rents);
            } else {
                List<Rent> rents = this.rentService.findByUserIdAndDevolutionAfter(userId);
                return ResponseEntity.ok().body(rents);
            }
        } else if ("false".equals(active)) {
            if (userId == null) {
                List<Rent> rents = this.rentService.findByDevolutionBefore();
                return ResponseEntity.ok().body(rents);
            } else {
                List<Rent> rents = this.rentService.findByUserIdAndDevolutionBefore(userId);
                return ResponseEntity.ok().body(rents);
            }
        }
        List<Rent> rents = this.rentService.findAll();
        return ResponseEntity.ok().body(rents);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Rent obj) {
        if (obj.getDevolution() != null) {
            if (obj.getDevolution().isBefore(obj.getRetreat().plusDays(2))) {
                obj.setDevolution(obj.getRetreat().plusDays(2));
            }
        } else {
            obj.setDevolution(obj.getRetreat().plusDays(2));
        }
        this.rentService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Rent obj, @PathVariable Long id) {
        obj.setId(id);
        if (obj.getDevolution() != null) {
            if (obj.getDevolution().isBefore(obj.getRetreat().plusDays(2))) {
                obj.setDevolution(obj.getRetreat().plusDays(2));
            }
        } else {
            obj.setDevolution(obj.getRetreat().plusDays(2));
        }
        this.rentService.update(obj);
        return ResponseEntity.noContent().build();
    }
}
