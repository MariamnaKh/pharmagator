package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/price")
public class PriceController {
    private final PriceRepository priceRepository;

    @GetMapping
    public ResponseEntity<List<Price>> getAll() {
        return ResponseEntity.ok(priceRepository.findAll());
    }

    @GetMapping("/get/{pharmacyId}/{medicineId}")
    public ResponseEntity<Price> getById(@PathVariable Long pharmacyId,
                                         @PathVariable Long medicineId) {
        Optional<Price> findById = priceRepository.findById(new PriceId(pharmacyId, medicineId));
        if (findById.isPresent()) {
            return new ResponseEntity<>(findById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{pharmacyId}/{medicineId}")
    public ResponseEntity<Price> deletePrice(@PathVariable Long pharmacyId,
                                             @PathVariable Long medicineId) {
        PriceId price = new PriceId(pharmacyId, medicineId);
        Optional<Price> findById = priceRepository.findById(price);
        if (findById.isPresent()) {
            priceRepository.deleteById(price);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Price> createPrice(@RequestBody Price price) {
        priceRepository.save(price);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Price> updatePrice(@RequestBody Price price) {
        Optional<Price> findById = priceRepository.findByPharmacyIdAndMedicineId(price.getPharmacyId(),
                price.getMedicineId());
        if (!findById.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        priceRepository.save(price);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }
}