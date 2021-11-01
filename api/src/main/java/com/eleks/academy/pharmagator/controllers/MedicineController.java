package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.MedicineDto;
import com.eleks.academy.pharmagator.services.impl.MedicineServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicines")
public class MedicineController {

    private final MedicineServiceImpl medicineService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MedicineDto> getAll() {

        return medicineService.findAll();

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MedicineDto getById(@PathVariable("id") Long id) {

        return medicineService.getById(id);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMedicine(@PathVariable("id") Long id) {

        medicineService.deleteMedicine(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicineDto createMedicine(@RequestBody MedicineDto medicine) {

        return medicineService.createMedicine(medicine);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MedicineDto updateMedicine(@PathVariable("id") Long id,
                                      @RequestBody MedicineDto medicine) {

        return medicineService.updateMedicine(id, medicine);

    }

}
