package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dto.PriceDto;

import java.util.List;

public interface PriceService {

    public List<PriceDto> getAll();

    public PriceDto getById(Long pharmacyId, Long medicineId);

    public void deletePrice(Long pharmacyId, Long medicineId);

    public PriceDto createPrice(PriceDto price);

    public PriceDto updatePrice(Long pharmacyId, Long medicineId, PriceDto price);

}
