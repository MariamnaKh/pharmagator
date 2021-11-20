package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.projections.MedicinePrice;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PdfExportService {

    private final PriceRepository priceRepository;
    private final PharmacyRepository pharmacyRepository;

    public void getExportData(HttpServletResponse response) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
        } catch (DocumentException doc) {
            throw new DocumentException("Unable to create pdf file");
        } catch (IOException e) {
            throw new IllegalStateException("Unable to obtain writer");
        }

        document.open();

        Paragraph p = new Paragraph("List of medicines", setFont());
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        List<Pharmacy> pharmaciesList = pharmacyRepository.findAll();

        PdfPTable table = new PdfPTable(pharmaciesList.size() + 1);
        table.setSpacingBefore(10);

        HashMap<Long, Integer> columnMapping = new HashMap<>(pharmaciesList.size());

        writeHeader(table, columnMapping, pharmaciesList);

        Map<String, Map<Long, BigDecimal>> prices = getPrices();
        prices.forEach((medicineTitle, phs) ->
                writeTableData(table, medicineTitle, columnMapping, phs));

        document.add(table);

        document.close();
    }

    private void writeHeader(PdfPTable table, HashMap<Long, Integer> columnMapping, List<Pharmacy> pharmacy) {
        int index = 1;
        PdfPCell cell = new PdfPCell(new Phrase("Medicines"));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell);

        for (Pharmacy p : pharmacy) {
            columnMapping.put(p.getId(), index++);
            cell.setPhrase(new Phrase(p.getName()));
            table.addCell(cell);
        }
    }

    private void writeTableData(PdfPTable table, String title, HashMap<Long, Integer> columnMapping,
                                Map<Long, BigDecimal> phs) {

        PdfPCell medCell = new PdfPCell(new Phrase(title));
        table.addCell(medCell);

        PdfPCell[] pdfPCells = new PdfPCell[columnMapping.size()];

        phs.forEach((pharmacyId, price) -> {
            pdfPCells[columnMapping.get(pharmacyId) - 1] = new PdfPCell(new Phrase(price.toString()));
        });

        for (PdfPCell c : pdfPCells) {
            if (c == null) {
                c = new PdfPCell(new Phrase(""));
            }
            table.addCell(c);
        }
    }

    private Font setFont() {
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        return font;
    }

    private Map<String, Map<Long, BigDecimal>> getPrices() {
        return priceRepository.findAllMedicinesPrices()
                .stream()
                .collect(Collectors.groupingBy(MedicinePrice::getTitle,
                        Collectors.toMap(MedicinePrice::getPharmacyId, MedicinePrice::getPrice)));
    }

}

