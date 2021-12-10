package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.constants.ErrorMessage;
import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.dto.mappers.PriceDtoMapper;
import com.eleks.academy.pharmagator.exceptions.IdentifierNotFoundException;
import com.eleks.academy.pharmagator.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

        return priceService.findById(pharmacyId, medicineId)
                .map(price -> ResponseEntity.ok(PriceDtoMapper.toPriceDto(price)))
                .orElseThrow(() -> new IdentifierNotFoundException(ErrorMessage.PRICE_NOT_FOUND_BY_ID));
    }

    @PutMapping("/pharmacyId/{pharmacyId:[\\d]+}/medicineId/{medicineId:[\\d]+}")
    public ResponseEntity<PriceDto> update(
            @Valid @RequestBody PriceDto priceDto,
            @PathVariable Long pharmacyId,
            @PathVariable Long medicineId) {

        return priceService.update(pharmacyId, medicineId, priceDto)
                .map(price -> ResponseEntity.ok(PriceDtoMapper.toPriceDto(price)))
                .orElseThrow(() -> new IdentifierNotFoundException(ErrorMessage.PRICE_NOT_FOUND_BY_ID));
    }

    @DeleteMapping("/pharmacyId/{pharmacyId:[\\d]+}/medicineId/{medicineId:[\\d]+}")
    public ResponseEntity<?> delete(
            @PathVariable Long pharmacyId,
            @PathVariable Long medicineId) {

        priceService.deleteById(pharmacyId, medicineId);
        return ResponseEntity.noContent().build();
    }

}

