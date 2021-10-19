package com.eleks.academy.pharmagator.dataproviders.dto.rozetka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineIdsDto {
    private List<Long> ids;
}
