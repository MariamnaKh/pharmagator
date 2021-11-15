package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.dto.mappers.PharmacyDtoMapper;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.services.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pharmacies")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @GetMapping
    public List<PharmacyDto> getAll() {

        return PharmacyDtoMapper.toPharmacyDto(pharmacyService.findAll());
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<PharmacyDto> getById(@PathVariable Long id) {
        Optional<Pharmacy> byId = pharmacyService.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(PharmacyDtoMapper.toPharmacyDto(byId.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PharmacyDto> create(@Valid @RequestBody PharmacyDto pharmacyDto) {
        return ResponseEntity.ok(PharmacyDtoMapper.toPharmacyDto(pharmacyService.save(pharmacyDto)));
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<PharmacyDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PharmacyDto pharmacyDto) {

        Optional<Pharmacy> update = pharmacyService.update(id, pharmacyDto);
        if (update.isPresent()) {
            return ResponseEntity.ok(PharmacyDtoMapper.toPharmacyDto(update.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        pharmacyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
