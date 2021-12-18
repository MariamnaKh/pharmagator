package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PharmacyMedServiceDataProvider implements DataProvider {

    @Value("${pharmagator.data-providers.med-service.url}")
    private String categoryPath;

    @Value("${pharmagator.data-providers.med-service.pharmacy-name}")
    private String pharmacyName;

    @Value("${pharmagator.data-providers.med-service.page-limit}")
    private int pageNumbers;

    @Override
    public Stream<MedicineDto> loadData() {
        Stream<MedicineDto> dtoStream = Stream.of();
        String path = categoryPath;
        int page = 1;

        while (page <= pageNumbers && !path.isEmpty()) {
            dtoStream = Stream.concat(dtoStream, fetchMedicines(path));
            path = getPageLinks(path);
            page++;
        }

        return dtoStream;
    }

    private String getPageLinks(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements otherLinks = document.select("li.bx-pag-next").select("a");

            if (!otherLinks.isEmpty()) {
                return otherLinks.attr("abs:href");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Stream<MedicineDto> fetchMedicines(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document.select("div.bx_catalog_item")
                    .stream()
                    .filter(p -> p.select("button.mc-button").text().contains("У кошик"))
                    .map(this::mapToMedicineDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BigDecimal getPrice(String str) {
        String[] arr = str.split("\\s+");
        return new BigDecimal(arr[0].replaceAll(",", "."));
    }

    private MedicineDto mapToMedicineDto(Element element) {
        return MedicineDto.builder()
                .externalId(element.select("div.bx_catalog_item_controls_blocktwo").attr("id"))
                .price(getPrice(element.select("div.bx_price").text()))
                .title(element.select("div.bx_catalog_item_title").select("a").text())
                .pharmacyName(pharmacyName)
                .build();
    }

}

