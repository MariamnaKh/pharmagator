package com.eleks.academy.pharmagator.dataproviders;

import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.rozetka.CategoryDto;
import com.eleks.academy.pharmagator.dataproviders.dto.rozetka.MedicineIdsDto;
import com.eleks.academy.pharmagator.dataproviders.dto.rozetka.RozetkaMedicineDto;
import com.eleks.academy.pharmagator.dataproviders.dto.rozetka.RozetkaMedicinesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PharmacyRozetkaDataProvider implements DataProvider{
    private final WebClient rozetkaWebClient;
    @Value("${pharmagator.data-providers.apteka-rozetka.category-fetch-url}")
    private String categoriesFetchUrl;
    @Value("${pharmagator.data-providers.apteka-rozetka.ids-fetch-url}")
    private String idsFetchUrl;
    @Value("${pharmagator.data-providers.apteka-rozetka.medicines-fetch-url}")
    private String medicineFetchUrl;

    @Override
    public Stream<MedicineDto> loadData() {
        return this.fetchCategories().stream()
                .map(CategoryDto::getChildren)
                .flatMap(Collection::stream)
                .map(CategoryDto::getId)
                .flatMap(this::fetchMedicines);
    }

    private List<CategoryDto> fetchCategories() {
        return this.rozetkaWebClient.get().uri(categoriesFetchUrl)
                .retrieve().bodyToMono(new ParameterizedTypeReference<List<CategoryDto>>() {
                }).block();
    }

    private Stream<MedicineDto> fetchMedicines(Long id) {
        MedicineIdsDto medicineIdsDto = fetchMedicineIds(id);
        List<Long> listOfIds = medicineIdsDto.getIds();
        String idsString = listOfIds.stream()
                .map( n -> n.toString() )
                .collect( Collectors.joining( "," ) );
        RozetkaMedicinesResponse rozetkaMedicinesResponse = this.rozetkaWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(medicineFetchUrl)
                        .queryParam("product_ids", idsString)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RozetkaMedicinesResponse>() {
                })
                .block();
        if (rozetkaMedicinesResponse != null) {
            return rozetkaMedicinesResponse.getProducts().stream()
                    .map(this::mapToMedicineDto);
        }
        return Stream.of();
    }

    private MedicineIdsDto fetchMedicineIds(Long id) {
        String ids = Long.toString(id);
        return this.rozetkaWebClient.get().uri(uriBuilder -> uriBuilder.path(idsFetchUrl)
                .queryParam("category_id", ids).queryParam("sell_status", "available")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<MedicineIdsDto>() {
                })
                .block();
    }

    private MedicineDto mapToMedicineDto(RozetkaMedicineDto rozetkaMedicineDto) {
        return MedicineDto.builder()
                .externalId(rozetkaMedicineDto.getId().toString())
                .price(rozetkaMedicineDto.getPrice())
                .title(rozetkaMedicineDto.getTitle())
                .build();
    }

}
