package com.eleks.academy.pharmagator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

    @Min(value = 0)
    private BigDecimal price;
    private String externalId;
    private Instant updatedAt;

}
