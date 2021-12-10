package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.constants.ErrorMessage;
import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.dto.mappers.PharmacyDtoMapper;
import com.eleks.academy.pharmagator.exceptions.IdentifierNotFoundException;
import com.eleks.academy.pharmagator.services.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        return pharmacyService.findById(id)
                .map(pharmacy -> ResponseEntity.ok(PharmacyDtoMapper.toPharmacyDto(pharmacy)))
                .orElseThrow(() -> new IdentifierNotFoundException(String.format(ErrorMessage.PHARMACY_NOT_FOUND_BY_ID, id)));
    }

    @PostMapping
    public ResponseEntity<PharmacyDto> create(@Valid @RequestBody PharmacyDto pharmacyDto) {
        return ResponseEntity.ok(PharmacyDtoMapper.toPharmacyDto(pharmacyService.save(pharmacyDto)));
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<PharmacyDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PharmacyDto pharmacyDto) {

        return pharmacyService.update(id, pharmacyDto)
                .map(pharmacy -> ResponseEntity.ok(PharmacyDtoMapper.toPharmacyDto(pharmacy)))
                .orElseThrow(() -> new IdentifierNotFoundException(String.format(ErrorMessage.PHARMACY_NOT_FOUND_BY_ID, id)));
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        pharmacyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
