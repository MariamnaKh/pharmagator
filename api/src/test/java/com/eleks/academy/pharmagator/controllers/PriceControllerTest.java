package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.PriceDto;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.services.PriceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private Price price;
    private Price price2;
    private List<Price> priceList;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PriceController priceController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        price = new Price(3L, 5L, new BigDecimal("24"), "1234", Instant.now());
        price2 = new Price(5L, 3L, new BigDecimal("245"), "12345", Instant.now());
        priceList = Arrays.asList(price, price2);

    }

    @AfterEach
    void tearDown() {

        price = null;
        price2 = null;
        priceList = null;

    }

    @Test
    public void getAllPrices_returnsPrices() throws Exception {

        when(priceService.findAll()).thenReturn(priceList);
        mockMvc.perform(MockMvcRequestBuilders.get(URI).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(priceService, times(1)).findAll();

    }

    @Test
    public void DeleteById_ShouldDeletePrice() throws Exception {

        doNothing().when(priceService).deleteById(price.getPharmacyId(), price.getMedicineId());
        mockMvc.perform(delete(URI + "/" + "pharmacyId/{pharmacyId}/medicineId/{medicineId}",
                        price.getPharmacyId(), price.getMedicineId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void findById_testedResourceNotFoundException() throws Exception {
        when(priceService.findById(anyLong(), anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get(URI + "/" + "pharmacyId/{pharmacyId}/medicineId/{medicineId}",
                        1234L, 456L))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_testResourceNotFoundException() throws Exception {
        when(priceService.update(anyLong(), anyLong(), any(PriceDto.class))).thenReturn(Optional.empty());
        mockMvc.perform(put(URI + "/" + "pharmacyId/{pharmacyId}/medicineId/{medicineId}",
                        price.getPharmacyId(), price.getMedicineId()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(price)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void GetMappingOfPrice_ShouldReturnRespectivePrice() throws Exception {

        when(priceService.findById(price2.getPharmacyId(), price2.getMedicineId())).thenReturn(Optional.ofNullable(price2));
        mockMvc.perform(get(URI + "/" + "pharmacyId/{pharmacyId}/medicineId/{medicineId}",
                        price2.getPharmacyId(), price2.getMedicineId()).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.price").value(price2.getPrice()))
                .andExpect(jsonPath("$.externalId").value(price2.getExternalId()));

    }

    @Test
    public void putMappingOfPrice_priceIsUpdated() throws Exception {

        when(priceService.update(anyLong(), anyLong(), any(PriceDto.class))).thenReturn(Optional.ofNullable(price));
        mockMvc.perform(put(URI + "/" + "pharmacyId/{pharmacyId}/medicineId/{medicineId}",
                        price.getPharmacyId(), price.getMedicineId()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(price)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(price.getPrice()))
                .andExpect(jsonPath("$.externalId").value(price.getExternalId()));
        verify(priceService, times(1)).update(anyLong(), anyLong(), any(PriceDto.class));

    }

}
