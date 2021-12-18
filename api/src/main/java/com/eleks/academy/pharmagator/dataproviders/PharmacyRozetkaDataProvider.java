package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.rozetka.RozetkaIdsResponse;
import com.eleks.academy.pharmagator.dataproviders.dto.rozetka.RozetkaIdsResponseData;
import com.eleks.academy.pharmagator.dataproviders.dto.rozetka.RozetkaMedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.rozetka.RozetkaMedicinesResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
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

    @Value("${pharmagator.data-providers.apteka-rozetka.medicines-fetch-url}")
    private String productsPath;

    @Value("${pharmagator.data-providers.apteka-rozetka.pharmacy-name}")
    private String pharmacyName;

    @Value("${pharmagator.data-providers.apteka-rozetka.page-limit}")
    private Long pageLimit;

    public PharmacyRozetkaDataProvider(@Qualifier("pharmacyRozetkaWebClient") WebClient rozetkaWebClient) {

        this.rozetkaWebClient = rozetkaWebClient;

    }

    @Override
    public Stream<MedicineDto> loadData() {
        return Stream.iterate(1, page -> page + 1)
                .limit(pageLimit)
                .map(this::fetchMedicineIds)
                .flatMap(Optional::stream)
                .takeWhile(response -> response.getShowNext() != 0)
                .map(RozetkaIdsResponseData::getIds)
                .flatMap(this::fetchMedicines);
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

        return Optional.ofNullable(rozetkaMedicinesResponse).map(RozetkaMedicinesResponse::getData).stream()
                .flatMap(Collection::stream)
                .filter(rozetkaMedicineDto -> Objects.nonNull(rozetkaMedicineDto.getId()) && Objects.nonNull(rozetkaMedicineDto.getTitle()))
                .map(this::mapToMedicineDto);
    }

    private Optional<RozetkaIdsResponseData> fetchMedicineIds(int page) {
        RozetkaIdsResponse rozetkaIdsResponse = this.rozetkaWebClient.get().
                uri(uriBuilder -> uriBuilder.path(idsFetchUrl)
                        .queryParam("category_id", categoryId)
                        .queryParam("sell_status", sellStatus)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(RozetkaIdsResponse.class)
                .block();
        return Optional.ofNullable(rozetkaIdsResponse)
                .map(RozetkaIdsResponse::getData);
    }

    private MedicineDto mapToMedicineDto(RozetkaMedicineDto rozetkaMedicineDto) {
        return MedicineDto.builder()
                .externalId(rozetkaMedicineDto.getId().toString())
                .price(rozetkaMedicineDto.getPrice())
                .title(rozetkaMedicineDto.getTitle())
                .pharmacyName(pharmacyName)
                .build();
    }

}
