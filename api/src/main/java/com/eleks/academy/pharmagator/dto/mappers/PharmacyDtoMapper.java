package com.eleks.academy.pharmagator.dto.mappers;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PharmacyDtoMapper {

    public Pharmacy toPharmacyEntity(PharmacyDto dto) {

        Pharmacy pharmacy = new Pharmacy();
        //pharmacy.setId(dto.getId());
        pharmacy.setName(dto.getName());
        pharmacy.setMedicineLinkTemplate(dto.getMedicineLinkTemplate());
        return pharmacy;

    }

    public PharmacyDto toPharmacyDto(Pharmacy pharmacy) {

        PharmacyDto pharmacyDto = new PharmacyDto();
        pharmacyDto.setId(pharmacy.getId());
        pharmacyDto.setName(pharmacy.getName());
        pharmacyDto.setMedicineLinkTemplate(pharmacy.getMedicineLinkTemplate());
        return pharmacyDto;

    }

    public List<PharmacyDto> toPharmacyDto(List<Pharmacy> pharmacies) {

        return pharmacies.stream().map(this::toPharmacyDto).collect(Collectors.toList());

    }

}
