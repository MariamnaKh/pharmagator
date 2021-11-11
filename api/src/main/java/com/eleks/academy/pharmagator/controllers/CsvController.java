package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.services.impl.CsvUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class CsvController {

    private final CsvUploadService csvUploadService;

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile) {
        if (!multipartFile.isEmpty() && multipartFile.getContentType().contentEquals("text/csv")) {
            csvUploadService.save(multipartFile);
        }
        return ResponseEntity.noContent().build();
    }

}
