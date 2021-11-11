package com.eleks.academy.pharmagator.util;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class CsvUtil {

    public static List<MedicineDto> parseBeans(InputStream inputStream) {
        BeanListProcessor<MedicineDto> rowProcessor = new BeanListProcessor<MedicineDto>(MedicineDto.class);

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator(System.lineSeparator());
        parserSettings.setProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(parserSettings);
        parser.parse(getReader(inputStream));
        return rowProcessor.getBeans();
    }

    private static Reader getReader(InputStream input) {
        try {
            return new InputStreamReader(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Unable to read input", e);
        }
    }

}
