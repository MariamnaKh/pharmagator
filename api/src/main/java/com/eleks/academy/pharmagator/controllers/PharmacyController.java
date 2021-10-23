package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.services.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pharmacies")
public class PharmacyController {
    private final PharmacyService pharmacyService;

    @GetMapping
    public List<PharmacyDto> getAll() {

        return pharmacyService.getAll();

    }

    @GetMapping("/{id}")
    public PharmacyDto getById(@PathVariable("id") Long id) {

        return pharmacyService.getById(id);

    }

    @DeleteMapping("/{id}")
    public void deletePharmacy(@PathVariable("id") Long id) {

        pharmacyService.deletePharmacy(id);

    }

    @PostMapping
    public PharmacyDto createPharmacy(@RequestBody PharmacyDto pharmacy) {

        return pharmacyService.createPharmacy(pharmacy);

    }

    @PutMapping("/{id}")
    public PharmacyDto updatePharmacy(@PathVariable("id") Long id,
                                                   @RequestBody PharmacyDto pharmacy) {

        return pharmacyService.updatePharmacy(id, pharmacy);

    }
}
