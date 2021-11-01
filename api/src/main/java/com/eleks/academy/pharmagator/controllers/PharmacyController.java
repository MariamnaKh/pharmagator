package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.services.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pharmacies")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PharmacyDto> getAll() {

        return pharmacyService.getAll();

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PharmacyDto getById(@PathVariable("id") Long id) {

        return pharmacyService.getById(id);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePharmacy(@PathVariable("id") Long id) {

        pharmacyService.deletePharmacy(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PharmacyDto createPharmacy(@RequestBody PharmacyDto pharmacy) {

        return pharmacyService.createPharmacy(pharmacy);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PharmacyDto updatePharmacy(@PathVariable("id") Long id,
                                      @RequestBody PharmacyDto pharmacy) {

        return pharmacyService.updatePharmacy(id, pharmacy);

    }

}
