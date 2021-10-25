package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.dto.mappers.PriceDtoMapper;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.entities.PriceId;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import com.eleks.academy.pharmagator.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    @Override
    public List<PriceDto> getAll() {

        return PriceDtoMapper.toPriceDto(priceRepository.findAll());

    }

    @Override
    public PriceDto getById(Long pharmacyId, Long medicineId) {

        Optional<Price> price = priceRepository.findByPharmacyIdAndMedicineId(pharmacyId, medicineId);
        if(price.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return PriceDtoMapper.toPriceDto(price.get());

    }

    @Override
    public void deletePrice(Long pharmacyId, Long medicineId) {

        Optional<Price> price = priceRepository.findByPharmacyIdAndMedicineId(pharmacyId, medicineId);
        if(price.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        priceRepository.deleteById(new PriceId(pharmacyId, medicineId));

    }

    @Override
    public PriceDto createPrice(PriceDto price) {

        return PriceDtoMapper.toPriceDto(priceRepository.save(PriceDtoMapper.toPriceEntity(price)));

    }

    @Override
    public PriceDto updatePrice(Long pharmacyId, Long medicineId, PriceDto priceDto) {

        Optional<Price> price = priceRepository.findById(new PriceId(pharmacyId, medicineId));
        if(price.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Price tmp = price.get();
        tmp.setPrice(priceDto.getPrice());
        tmp.setUpdatedAt(Instant.now());
        tmp.setExternalId(priceDto.getExternalId());
        return PriceDtoMapper.toPriceDto(priceRepository.save(tmp));

    }
}
