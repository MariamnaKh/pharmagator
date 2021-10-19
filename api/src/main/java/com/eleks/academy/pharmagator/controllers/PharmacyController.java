package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pharmacies")
public class PharmacyController {
    private final PharmacyRepository pharmacyRepository;

    @GetMapping
    public ResponseEntity<List<Pharmacy>> getAll() {
        return ResponseEntity.ok(pharmacyRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pharmacy> getById(@PathVariable("id") Long id) {
        Optional<Pharmacy> findById = pharmacyRepository.findById(id);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pharmacy> deletePharmacy(@PathVariable("id") Long id) {
        Optional<Pharmacy> findById = pharmacyRepository.findById(id);
        if (findById.isPresent()) {
            pharmacyRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Pharmacy> createPharmacy(@RequestBody Pharmacy pharmacy) {
        Optional<Pharmacy> findById = pharmacyRepository.findById(pharmacy.getId());
        if (findById.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        pharmacyRepository.save(pharmacy);
        return new ResponseEntity<>(pharmacy, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pharmacy> updatePharmacy(@PathVariable("id") Long id,
                                                   @RequestBody Pharmacy pharmacy) {
        Optional<Pharmacy> findById = pharmacyRepository.findById(pharmacy.getId());
        if (!findById.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pharmacy.setId(id);
        pharmacyRepository.save(pharmacy);
        return new ResponseEntity<>(pharmacy, HttpStatus.OK);
    }
}
