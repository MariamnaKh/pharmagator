package com.eleks.academy.pharmagator.dto.mappers;

import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.entities.Price;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceDtoMapper {

    public Price toPriceEntity(PriceDto dto) {

        Price price = new Price();
        price.setPrice(dto.getPrice());
        price.setExternalId(dto.getExternalId());
        price.setUpdatedAt(dto.getUpdatedAt());
        return price;

    }

    public PriceDto toPriceDto(Price price) {

        PriceDto priceDto = new PriceDto();
        priceDto.setPrice(price.getPrice());
        priceDto.setExternalId(price.getExternalId());
        priceDto.setUpdatedAt(price.getUpdatedAt());
        priceDto.setMedicineId(priceDto.getMedicineId());
        priceDto.setPharmacyId(priceDto.getPharmacyId());
        return priceDto;

    }

    public List<PriceDto> toPriceDto(List<Price> priceList) {

        return priceList.stream().map(this::toPriceDto).collect(Collectors.toList());

    }

}
