package com.eleks.academy.pharmagator.dto.mappers;

import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.entities.Price;

import java.util.List;
import java.util.stream.Collectors;

public class PriceDtoMapper {

    public static Price toPriceEntity(PriceDto dto) {

        Price price = new Price();
        price.setPrice(dto.getPrice());
        price.setExternalId(dto.getExternalId());
        price.setUpdatedAt(dto.getUpdatedAt());
        return price;

    }

    public static List<Price> toPriceEntity(List<PriceDto> dto) {
        return dto.stream().map(PriceDtoMapper::toPriceEntity).collect(Collectors.toList());
    }

    public static PriceDto toPriceDto(Price price) {

        PriceDto priceDto = new PriceDto();
        priceDto.setPrice(price.getPrice());
        priceDto.setExternalId(price.getExternalId());
        priceDto.setUpdatedAt(price.getUpdatedAt());
        return priceDto;

    }

    public static List<PriceDto> toPriceDto(List<Price> priceList) {

        return priceList.stream().map(PriceDtoMapper::toPriceDto).collect(Collectors.toList());

    }

}
