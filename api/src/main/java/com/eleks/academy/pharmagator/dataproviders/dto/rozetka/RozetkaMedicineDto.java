package com.eleks.academy.pharmagator.dataproviders.dto.rozetka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RozetkaMedicineDto {
    private Long id;
    private String title;
    private BigDecimal price;
}
