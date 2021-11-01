package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.services.PriceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PriceController.class)
public class PriceControllerTest {
    private final String URI = "/prices";

    @MockBean
    private PriceService priceService;
    private PriceDto price;
    private PriceDto price2;
    private List<PriceDto> priceList;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PriceController priceController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        price = new PriceDto(3L, 5L, new BigDecimal("24"), "1234", Instant.now());
        price2 = new PriceDto(5L, 3L, new BigDecimal("245"), "12345", Instant.now());
        priceList = Arrays.asList(price, price2);

    }

    @AfterEach
    void tearDown() {

        price = null;
        price2 = null;
        priceList = null;

    }

    @Test
    public void postMappingOfPrice_priceIsCreated() throws Exception {

        when(priceService.createPrice(any(PriceDto.class))).thenReturn(price);
        mockMvc.perform(post(URI).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(price)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pharmacyId").value(price.getPharmacyId()))
                .andExpect(jsonPath("$.medicineId").value(price.getMedicineId()))
                .andExpect(jsonPath("$.price").value(price.getPrice()))
                .andExpect(jsonPath("$.externalId").value(price.getExternalId()));
        verify(priceService, times(1)).createPrice(any(PriceDto.class));

    }

    @Test
    public void getAllPrices_returnsPrices() throws Exception {

        when(priceService.getAll()).thenReturn(priceList);
        mockMvc.perform(MockMvcRequestBuilders.get(URI).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(priceService, times(1)).getAll();

    }

    @Test
    public void DeleteById_ShouldDeletePrice() throws Exception {

        doNothing().when(priceService).deletePrice(price.getPharmacyId(), price.getMedicineId());
        mockMvc.perform(delete(URI + "/" + "pharmacies/{pharmacyId}/medicines/{medicineId}",
                        price.getPharmacyId(), price.getMedicineId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testedResourceNotFoundException() throws Exception {

        when(priceService.getById(anyLong(), anyLong())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get(URI + "/" + "pharmacies/{pharmacyId}/medicines/{medicineId}",
                        1234L, 456L))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void GetMappingOfPrice_ShouldReturnRespectivePrice() throws Exception {

        when(priceService.getById(price2.getPharmacyId(), price2.getMedicineId())).thenReturn(price2);
        mockMvc.perform(get(URI + "/" + "pharmacies/{pharmacyId}/medicines/{medicineId}",
                        price2.getPharmacyId(), price2.getMedicineId()).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.pharmacyId").value(price2.getPharmacyId()))
                .andExpect(jsonPath("$.medicineId").value(price2.getMedicineId()))
                .andExpect(jsonPath("$.price").value(price2.getPrice()))
                .andExpect(jsonPath("$.externalId").value(price2.getExternalId()));

    }

    @Test
    public void putMappingOfPrice_priceIsUpdated() throws Exception {

        when(priceService.updatePrice(anyLong(), anyLong(), any(PriceDto.class))).thenReturn(price);
        mockMvc.perform(put(URI + "/" + "pharmacies/{pharmacyId}/medicines/{medicineId}",
                        price.getPharmacyId(), price.getMedicineId()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(price)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pharmacyId").value(price.getPharmacyId()))
                .andExpect(jsonPath("$.medicineId").value(price.getMedicineId()))
                .andExpect(jsonPath("$.price").value(price.getPrice()))
                .andExpect(jsonPath("$.externalId").value(price.getExternalId()));
        verify(priceService, times(1)).updatePrice(anyLong(), anyLong(), any(PriceDto.class));

    }

}
