package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.services.impl.PdfExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicines/export")
public class PdfExportController {

    @Autowired
    private final PdfExportService exportService;

    @GetMapping("/pdf")
    public void exportToPDF(HttpServletResponse response) {
        response.setContentType("application/pdf");
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String currentDateTime = dateTime.format(formatter);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=medicines_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        exportService.getExportData(response);
    }

}
