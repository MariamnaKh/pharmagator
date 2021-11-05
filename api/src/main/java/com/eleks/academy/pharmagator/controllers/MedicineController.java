package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.MedicineDto;
import com.eleks.academy.pharmagator.dto.mappers.MedicineDtoMapper;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.services.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
        Optional<Medicine> byId = medicineService.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(MedicineDtoMapper.toMedicineDto(byId.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public MedicineDto create(@Valid @RequestBody MedicineDto medicineDto) {
        return MedicineDtoMapper.toMedicineDto(medicineService.save(medicineDto));
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<MedicineDto> update(
            @PathVariable Long id,
            @Valid @RequestBody MedicineDto medicineDto) {

        Optional<Medicine> update = medicineService.update(id, medicineDto);
        if (update.isPresent()) {
            return ResponseEntity.ok(MedicineDtoMapper.toMedicineDto(update.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        medicineService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
