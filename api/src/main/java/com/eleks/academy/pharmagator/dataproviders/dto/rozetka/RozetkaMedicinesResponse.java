package com.eleks.academy.pharmagator.dataproviders.dto.rozetka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RozetkaMedicinesResponse {
    private List<RozetkaMedicineDto> products;
}
