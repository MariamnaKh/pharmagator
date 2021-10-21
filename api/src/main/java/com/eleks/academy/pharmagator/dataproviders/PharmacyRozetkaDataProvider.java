package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.rozetka.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PharmacyRozetkaDataProvider implements DataProvider {

    private final WebClient rozetkaWebClient;

    @Value("${pharmagator.data-providers.apteka-rozetka.category-id}")
    private String categoryId;

    @Value("${pharmagator.data-providers.apteka-rozetka.ids-fetch-url}")
    private String idsFetchUrl;

    @Value("${pharmagator.data-providers.apteka-rozetka.medicines-fetch-url}")
    private String medicineFetchUrl;

    @Value("${pharmagator.data-providers.apteka-rozetka.sell-status}")
    private String sellStatus;

   @Override
    public Stream<MedicineDto> loadData() {

        RozetkaIdsResponseData rozetkaResponse;
        Stream<MedicineDto> medicineDto = Stream.of();
        int page = 1;
        do {
            rozetkaResponse = this.fetchMedicineIds(page);
            medicineDto = Stream.concat(medicineDto, this.fetchMedicines(rozetkaResponse.getIds()));
            page++;

        } while (rozetkaResponse.getShowNext() != 0);

        return medicineDto;

    }

    private Stream<MedicineDto> fetchMedicines(List<Long> ids) {

        String idsString = ids.stream()
                .map(n -> n.toString())
                .collect(Collectors.joining(","));

        RozetkaMedicinesResponse rozetkaMedicinesResponse = this.rozetkaWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(medicineFetchUrl)
                        .queryParam("product_ids", idsString)
                        .build())
                .retrieve()
                .bodyToMono(RozetkaMedicinesResponse.class)
                .block();
        if (rozetkaMedicinesResponse != null) {
            return rozetkaMedicinesResponse.getData().stream()
                    .map(this::mapToMedicineDto);
        }
        return Stream.empty();

    }

    private RozetkaIdsResponseData fetchMedicineIds(int page) {

        RozetkaIdsResponse rozetkaIdsResponse = this.rozetkaWebClient.get().
                uri(uriBuilder -> uriBuilder.path(idsFetchUrl)
                        .queryParam("category_id", categoryId)
                        .queryParam("sell_status", sellStatus)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(RozetkaIdsResponse.class)
                .block();
        return rozetkaIdsResponse.getData();

    }

    private MedicineDto mapToMedicineDto(RozetkaMedicineDto rozetkaMedicineDto) {

        return MedicineDto.builder()
                .externalId(rozetkaMedicineDto.getId().toString())
                .price(rozetkaMedicineDto.getPrice())
                .title(rozetkaMedicineDto.getTitle())
                .build();

    }

}
