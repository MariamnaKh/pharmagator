package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.constants.ErrorMessage;
import com.eleks.academy.pharmagator.dto.MedicineDto;
import com.eleks.academy.pharmagator.dto.mappers.MedicineDtoMapper;
import com.eleks.academy.pharmagator.exceptions.IdentifierNotFoundException;
import com.eleks.academy.pharmagator.services.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping
    public List<MedicineDto> getAll() {

        return MedicineDtoMapper.toMedicineDto(medicineService.findAll());
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<MedicineDto> getById(@PathVariable Long id) {
        return medicineService.findById(id)
                .map(medicine -> ResponseEntity.ok(MedicineDtoMapper.toMedicineDto(medicine)))
                .orElseThrow(() -> new IdentifierNotFoundException(String.format(ErrorMessage.MEDICINE_NOT_FOUND_BY_ID, id)));
    }

    @PostMapping
    public MedicineDto create(@Valid @RequestBody MedicineDto medicineDto) {
        return MedicineDtoMapper.toMedicineDto(medicineService.save(medicineDto));
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<MedicineDto> update(
            @PathVariable Long id,
            @Valid @RequestBody MedicineDto medicineDto) {

        return medicineService.update(id, medicineDto)
                .map(medicine -> ResponseEntity.ok(MedicineDtoMapper.toMedicineDto(medicine)))
                .orElseThrow(() -> new IdentifierNotFoundException(String.format(ErrorMessage.MEDICINE_NOT_FOUND_BY_ID, id)));
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        medicineService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
