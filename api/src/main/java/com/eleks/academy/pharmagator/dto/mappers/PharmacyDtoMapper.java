package com.eleks.academy.pharmagator.dto.mappers;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.entities.Pharmacy;

import java.util.List;
import java.util.stream.Collectors;

public class PharmacyDtoMapper {

    public static Pharmacy toPharmacyEntity(PharmacyDto dto) {

        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName(dto.getName());
        pharmacy.setMedicineLinkTemplate(dto.getMedicineLinkTemplate());
        return pharmacy;

    }

    public static List<Pharmacy> toPharmacyEntity(List<PharmacyDto> dto) {
        return dto.stream().map(PharmacyDtoMapper::toPharmacyEntity).collect(Collectors.toList());
    }

    public static PharmacyDto toPharmacyDto(Pharmacy pharmacy) {

        PharmacyDto pharmacyDto = new PharmacyDto();
        pharmacyDto.setName(pharmacy.getName());
        pharmacyDto.setMedicineLinkTemplate(pharmacy.getMedicineLinkTemplate());
        return pharmacyDto;

    }

    public static List<PharmacyDto> toPharmacyDto(List<Pharmacy> pharmacies) {

        return pharmacies.stream().map(PharmacyDtoMapper::toPharmacyDto).collect(Collectors.toList());

    }

}
