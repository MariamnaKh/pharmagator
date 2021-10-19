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
@RequestMapping("/medicines")
public class MedicineController {
    private final MedicineRepository medicineRepository;

    @GetMapping
    public ResponseEntity<List<Medicine>> getAll() {
        return ResponseEntity.ok(medicineRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getById(@PathVariable("id") Long id) {
        Optional<Medicine> findById = medicineRepository.findById(id);
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Medicine> deleteMedicine(@PathVariable("id") Long id) {
        Optional<Medicine> findById = medicineRepository.findById(id);
        if (findById.isPresent()) {
            medicineRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        medicineRepository.save(medicine);
        return new ResponseEntity<>(medicine, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable("id") Long id,
                                                   @RequestBody Medicine medicine) {
        Optional<Medicine> findById = medicineRepository.findById(medicine.getId());
        if (!findById.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        medicine.setId(id);
        medicineRepository.save(medicine);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
