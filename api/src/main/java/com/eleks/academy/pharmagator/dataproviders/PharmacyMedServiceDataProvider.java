package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PharmacyMedServiceDataProvider {

    @Value("${pharmagator.data-providers.med-service.url}")
    private String categoryPath;

    @Value("${pharmagator.data-providers.med-service.pharmacy-name}")
    private String pharmacyName;

    //private HashSet<String> links;


//    @Override
//    public Stream<MedicineDto> loadData() {
//        Stream<MedicineDto> dtoStream = Stream.of();
//        String path = pharmacyName;
//
//        do {
//            dtoStream = Stream.concat(dtoStream, fetchMedicines(path));
//            path = getPageLinks(path);
//        } while(!path.isEmpty());
//
//        return dtoStream;
//        //getPageLinks(categoryPath);
//        //return links.stream().flatMap(l -> fetchMedicines(l));
//    }

    private String getPageLinks(String url) {
            try {
                Document document = Jsoup.connect(url).get();
                Elements otherLinks = document.select("li.bx-pag-next").select("a");

                if(!otherLinks.isEmpty()) {
                    return otherLinks.attr("abs:href");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }

//    private void getPageLinks(String url) {
//        links = new HashSet<String>();
//        if (!links.contains(url)) {
//            try {
//                Document document = Jsoup.connect(url).get();
//                Elements otherLinks = document.select("li.bx-pag-next").select("a");
//
//                if(!otherLinks.isEmpty()) {
//                    getPageLinks(otherLinks.attr("abs:href"));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private Stream<MedicineDto> fetchMedicines(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document.select("div.bx_catalog_item").stream().filter(p -> p.select("button.mc-button").text().contains("У кошик")).map(this::mapToMedicineDto);
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
                .externalId(element.select("div.bx_catalog_item_title").select("a").attr("abs:href"))
                .price(getPrice(element.select("div.bx_price").text()))
                .title(element.select("div.bx_catalog_item_title").select("a").text())
                .pharmacyName(pharmacyName)
                .build();
    }

}

