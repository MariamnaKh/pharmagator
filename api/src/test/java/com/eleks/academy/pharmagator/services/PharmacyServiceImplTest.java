package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.dto.mappers.PharmacyDtoMapper;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.services.impl.PharmacyServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PharmacyServiceImplTest {

    @Mock
    PharmacyRepository pharmacyRepository;

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;
    Pharmacy pharmacy;
    Pharmacy pharmacy2;
    List<Pharmacy> pharmacyList;

    @BeforeEach
    public void setup() {

        pharmacy = new Pharmacy(8L, "My pharma", "linkk.com");
        pharmacy2 = new Pharmacy(9L, "Second pharma", "secongPharma.com");
        pharmacyList = Arrays.asList(pharmacy, pharmacy2);

    }

    @AfterEach
    void tearDown() {

        pharmacy = null;
        pharmacy2 = null;
        pharmacyList = null;

    }

    @Test
    public void getAllPharmacies() {

        when(pharmacyRepository.findAll()).thenReturn(pharmacyList);
        List<PharmacyDto> pharmacyList = pharmacyService.getAll();
        assertEquals(2, pharmacyList.size());

    }

    @Test
    public void givenPharmacy_CreateNewPharmacy() {

        when(pharmacyRepository.save(any(Pharmacy.class))).thenReturn(pharmacy);
        PharmacyDto savedPharmacy = pharmacyService.createPharmacy(PharmacyDtoMapper.toPharmacyDto(pharmacy));
        verify(pharmacyRepository, times(1)).save(any(Pharmacy.class));
        assertEquals(pharmacy.getId(), savedPharmacy.getId());
        assertEquals(pharmacy.getName(), savedPharmacy.getName());
        assertEquals(pharmacy.getMedicineLinkTemplate(), savedPharmacy.getMedicineLinkTemplate());

    }

    @Test
    public void givenPharmacy_TestById() {

        when(pharmacyRepository.findById(anyLong()))
                .thenReturn(Optional.of(pharmacy));
        PharmacyDto pharmacyDto = pharmacyService.getById(pharmacy.getId());
        verify(pharmacyRepository, times(1)).findById(anyLong());
        assertEquals(pharmacy.getId(), pharmacyDto.getId());
        assertEquals(pharmacy.getName(), pharmacyDto.getName());
        assertEquals(pharmacy.getMedicineLinkTemplate(), pharmacyDto.getMedicineLinkTemplate());

    }

    @Test
    public void deleteMethodThrowsResponseStatus() {

        final Long pharmacyId = 56L;
        assertThatThrownBy(() -> pharmacyService.deletePharmacy(pharmacyId))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    public void givenPharmacy_UpdatePharmacy() {

        when(pharmacyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(pharmacy));
        when(pharmacyRepository.save(any(Pharmacy.class))).thenReturn(pharmacy);
        PharmacyDto savedPharmacy = pharmacyService.updatePharmacy(anyLong(), PharmacyDtoMapper.toPharmacyDto(pharmacy));
        assertThat(savedPharmacy.getName()).isNotNull();
        assertEquals(pharmacy.getName(), savedPharmacy.getName());

    }

    @Test
    public void deletePharmacy() {

        when(pharmacyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(pharmacy));
        doNothing().when(pharmacyRepository).deleteById(anyLong());
        pharmacyService.deletePharmacy(pharmacy.getId());
        verify(pharmacyRepository, times(1)).deleteById(pharmacy.getId());

    }


}
