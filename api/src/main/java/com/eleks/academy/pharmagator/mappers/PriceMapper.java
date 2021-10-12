package com.eleks.academy.pharmagator.mappers;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Price;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class PriceMapper {
    public Price toPriceEntity(MedicineDto dto) {
        Price price = new Price();
        price.setPrice(dto.getPrice());
        price.setExternalId(dto.getExternalId());
        price.setUpdatedAt(Instant.now());
        return price;
    }
}
