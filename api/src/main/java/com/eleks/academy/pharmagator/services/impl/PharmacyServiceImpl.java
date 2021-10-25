package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.dto.mappers.PharmacyDtoMapper;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.services.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;


    @Override
    public List<PharmacyDto> getAll() {

        return PharmacyDtoMapper.toPharmacyDto(pharmacyRepository.findAll());

    }

    @Override
    public PharmacyDto getById(Long id) {

        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(id);
        if(pharmacy.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return PharmacyDtoMapper.toPharmacyDto(pharmacy.get());

    }

    @Override
    public void deletePharmacy(Long id) {

        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(id);
        if(pharmacy.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        pharmacyRepository.deleteById(id);

    }

    @Override
    public PharmacyDto createPharmacy(PharmacyDto pharmacy) {

        return PharmacyDtoMapper.toPharmacyDto(pharmacyRepository
                .save(PharmacyDtoMapper.toPharmacyEntity(pharmacy)));

    }

    @Override
    public PharmacyDto updatePharmacy(Long id, PharmacyDto pharmacyDto) {

        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(id);
        if(pharmacy.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Pharmacy tmp = pharmacy.get();
        tmp.setName(pharmacyDto.getName());
        tmp.setMedicineLinkTemplate(pharmacyDto.getMedicineLinkTemplate());
        return PharmacyDtoMapper.toPharmacyDto(pharmacyRepository.save(tmp));

    }

}
