package com.example.demo.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.models.Rent.UpdateRent;
import com.example.demo.models.Rent.CreateRent;
import com.example.demo.models.Rent;
import com.example.demo.services.RentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rent")
@Validated
public class RentController {
    @Autowired
    private RentService rentService;

    @GetMapping("/{id}")
    public ResponseEntity<Rent> findById(@PathVariable Long id) {
        Rent obj = this.rentService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Validated(CreateRent.class)
    public ResponseEntity<Void> create(@Valid @RequestBody Rent obj) {
        this.rentService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(UpdateRent.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Rent obj, @PathVariable Long id) {
        obj.setId(id);
        this.rentService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.rentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
