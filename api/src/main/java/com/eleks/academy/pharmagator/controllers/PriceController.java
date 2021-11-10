package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.dto.mappers.PriceDtoMapper;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping
    public List<PriceDto> getAll() {

        return PriceDtoMapper.toPriceDto(priceService.findAll());
    }

    @GetMapping("/pharmacyId/{pharmacyId:[\\d]+}/medicineId/{medicineId:[\\d]+}")
    public ResponseEntity<PriceDto> getById(
            @PathVariable Long pharmacyId,
            @PathVariable Long medicineId) {

        Optional<Price> byId = priceService.findById(pharmacyId, medicineId);
        if (byId.isPresent()) {
            return ResponseEntity.ok(PriceDtoMapper.toPriceDto(byId.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/pharmacyId/{pharmacyId:[\\d]+}/medicineId/{medicineId:[\\d]+}")
    public ResponseEntity<PriceDto> update(
            @Valid @RequestBody PriceDto priceDto,
            @PathVariable Long pharmacyId,
            @PathVariable Long medicineId) {

        Optional<Price> update = priceService.update(pharmacyId, medicineId, priceDto);
        if (update.isPresent()) {
            return ResponseEntity.ok(PriceDtoMapper.toPriceDto(update.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/pharmacyId/{pharmacyId:[\\d]+}/medicineId/{medicineId:[\\d]+}")
    public ResponseEntity<?> delete(
            @PathVariable Long pharmacyId,
            @PathVariable Long medicineId) {

        priceService.deleteById(pharmacyId, medicineId);
        return ResponseEntity.noContent().build();
    }

}

