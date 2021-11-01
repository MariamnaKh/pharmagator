package com.eleks.academy.pharmagator.services;

import com.eleks.academy.pharmagator.dto.MedicineDto;
import com.eleks.academy.pharmagator.dto.mappers.MedicineDtoMapper;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import com.eleks.academy.pharmagator.services.impl.MedicineServiceImpl;
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
public class MedicineServiceImplTest {

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private MedicineServiceImpl medicineService;
    Medicine medicine1;
    Medicine medicine2;
    List<Medicine> medicines;

    @BeforeEach
    public void setUp() {

        medicine1 = new Medicine(1L, "Ibuprofen");
        medicine2 = new Medicine(3L, "Vitamin C");
        medicines = Arrays.asList(medicine1, medicine2);

    }

    @AfterEach
    void tearDown() {

        medicine1 = null;
        medicine2 = null;
        medicines = null;

    }

    @Test
    public void getAllMedicines() {

        when(medicineRepository.findAll()).thenReturn(medicines);
        List<MedicineDto> medicineDtoList = medicineService.findAll();
        assertEquals(2, medicineDtoList.size());

    }

    @Test
    public void givenMedicine_CreateNewMedicine() {

        when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine1);
        MedicineDto medicineDto = MedicineDtoMapper.toMedicineDto(medicine1);

        MedicineDto savedMedicine = medicineService.createMedicine(medicineDto);
        // then
        verify(medicineRepository, times(1)).save(any(Medicine.class));
        assertEquals(medicine1.getId(), savedMedicine.getId());
        assertEquals(medicine1.getTitle(), savedMedicine.getTitle());

    }

    @Test
    public void givenMedicine_TestById() {
        //given
        when(medicineRepository.findById(anyLong()))
                .thenReturn(Optional.of(medicine1));
        MedicineDto medicineDto = medicineService.getById(3L);

        // then
        verify(medicineRepository, times(1)).findById(anyLong());
        assertEquals(medicine1.getId(), medicineDto.getId());
        assertEquals(medicine1.getTitle(), medicineDto.getTitle());

    }

    @Test
    public void deleteMethodThrowsResponseStatus() {

        final Long medicineId = 8L;
        assertThatThrownBy(() -> medicineService.deleteMedicine(medicineId))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    public void givenMedicine_UpdateMedicine() {

        when(medicineRepository.findById(anyLong())).thenReturn(Optional.ofNullable(medicine1));
        when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine1);
        MedicineDto savedMedicine = medicineService.updateMedicine(anyLong(), MedicineDtoMapper.toMedicineDto(medicine1));
        assertThat(savedMedicine.getTitle()).isNotNull();
        assertEquals(medicine1.getTitle(), savedMedicine.getTitle());

    }

    @Test
    public void deleteMedicine() {

        when(medicineRepository.findById(anyLong())).thenReturn(Optional.ofNullable(medicine1));
        doNothing().when(medicineRepository).deleteById(anyLong());
        medicineService.deleteMedicine(medicine1.getId());
        verify(medicineRepository, times(1)).deleteById(medicine1.getId());

    }


}
