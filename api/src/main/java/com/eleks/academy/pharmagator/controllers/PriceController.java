package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prices")
public class PriceController {
    private final PriceService priceService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PriceDto> getAll() {

        return priceService.getAll();

    }

    @GetMapping("pharmacies/{pharmacyId}/medicines/{medicineId}")
    @ResponseStatus(HttpStatus.OK)
    public PriceDto getById(@PathVariable Long pharmacyId,
                            @PathVariable Long medicineId) {

        return priceService.getById(pharmacyId, medicineId);

    }

    @DeleteMapping("/pharmacies/{pharmacyId}/medicines/{medicineId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePrice(@PathVariable Long pharmacyId,
                            @PathVariable Long medicineId) {

        priceService.deletePrice(pharmacyId, medicineId);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PriceDto createPrice(@RequestBody PriceDto price) {

        return priceService.createPrice(price);

    }

    @PutMapping("pharmacies/{pharmacyId}/medicines/{medicineId}")
    @ResponseStatus(HttpStatus.OK)
    public PriceDto updatePrice(@PathVariable Long pharmacyId,
                                @PathVariable Long medicineId,
                                @RequestBody PriceDto price) {

        return priceService.updatePrice(pharmacyId, medicineId, price);

    }
}