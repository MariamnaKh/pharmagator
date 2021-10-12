package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/medicine")
public class MedicineController {
    private final MedicineRepository medicineRepository;

    @GetMapping
    public ResponseEntity<List<Medicine>> getAll() {
        return ResponseEntity.ok(medicineRepository.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Medicine> getById(@PathVariable("id") Long medicineId) {
        Optional<Medicine> findById = medicineRepository.findById(medicineId);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Medicine> deleteMedicine(@PathVariable("id") Long medicineId) {
        Optional<Medicine> findById = medicineRepository.findById(medicineId);
        if (findById.isPresent()) {
            medicineRepository.deleteById(medicineId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        Optional<Medicine> findById = medicineRepository.findById(medicine.getId());
        if (findById.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        medicineRepository.save(medicine);
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Medicine> updateMedicine(@RequestBody Medicine medicine) {
        Optional<Medicine> findByTitle = medicineRepository.findById(medicine.getId());
        if (!findByTitle.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        medicineRepository.save(medicine);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
