package com.eleks.academy.pharmagator.services.impl;

import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.projections.MedicinePrice;
import com.eleks.academy.pharmagator.repositories.PharmacyRepository;
import com.eleks.academy.pharmagator.repositories.PriceRepository;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvExportService {

    private final PriceRepository priceRepository;
    private final PharmacyRepository pharmacyRepository;

    public void getExportData(HttpServletResponse response) {
        List<Pharmacy> pharmaciesList = pharmacyRepository.findAll();

        HashMap<Long, Integer> columnMapping = new HashMap<>(pharmaciesList.size());

        try {
            CsvWriter csvWriter = new CsvWriter(response.getWriter(), setSettings());
            csvWriter.writeHeaders(writeCsvHeader(columnMapping, pharmaciesList));
            writeData(getPrices(), columnMapping, csvWriter);
            csvWriter.close();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to obtain writer");
        }
    }

    private List<String> writeCsvHeader(HashMap<Long, Integer> columnMapping, List<Pharmacy> pharmacy) {
        AtomicInteger mapping = new AtomicInteger(1);

        LinkedList<String> list = pharmacy.stream()
                .map(p -> {
                    columnMapping.put(p.getId(), mapping.getAndIncrement());
                    return p.getName();
                })
                .collect(Collectors.toCollection(LinkedList::new));
        list.addFirst("Medicines");

        return list;
    }

    private void writeData(Map<String, Map<Long, BigDecimal>> prices, HashMap<Long, Integer> columnMapping,
                           CsvWriter writer) {

        prices.forEach((pharmacy, phs) -> {
            {
                writer.addValue(pharmacy);
                phs.forEach((pharmacyId, price) ->
                        writer.addValue(columnMapping.get(pharmacyId), price));
                writer.writeValuesToRow();
            }
        });
    }

    private Map<String, Map<Long, BigDecimal>> getPrices() {
        return priceRepository.findAllMedicinesPrices()
                .stream()
                .collect(Collectors.groupingBy(MedicinePrice::getTitle,
                        Collectors.toMap(MedicinePrice::getPharmacyId, MedicinePrice::getPrice)));
    }

    private CsvWriterSettings setSettings() {
        CsvWriterSettings settings = new CsvWriterSettings();
        settings.setNullValue("-");
        settings.setEmptyValue("-");
        settings.setHeaderWritingEnabled(true);
        return settings;
    }

}

