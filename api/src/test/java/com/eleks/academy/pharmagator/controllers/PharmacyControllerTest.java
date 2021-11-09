package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.PharmacyDto;
import com.eleks.academy.pharmagator.entities.Pharmacy;
import com.eleks.academy.pharmagator.services.impl.PharmacyServiceImpl;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PharmacyController.class)
public class PharmacyControllerTest {

    private final String URI = "/pharmacies";

    @MockBean
    private PharmacyServiceImpl pharmacyService;
    private Pharmacy pharmacy;
    private Pharmacy pharmacy2;
    private List<Pharmacy> pharmacyList;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PharmacyController pharmacyController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        pharmacy = new Pharmacy(8L, "My pharma", "linkk.com");
        pharmacy2 = new Pharmacy(9L, "Second pharma", "secongPharma.com");
        pharmacyList = Arrays.asList(pharmacy, pharmacy2);

    }

    @AfterEach
    void tearDown() {

        pharmacy = null;
        pharmacy2 = null;
        pharmacyList = null;

    }

    @Test
    public void postMappingOfPharmacy_pharmacyIsCreated() throws Exception {

        when(pharmacyService.save(any(PharmacyDto.class))).thenReturn(pharmacy);
        mockMvc.perform(post(URI).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(pharmacy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(pharmacy.getName()))
                .andExpect(jsonPath("$.medicineLinkTemplate").value(pharmacy.getMedicineLinkTemplate()));
        verify(pharmacyService, times(1)).save(any(PharmacyDto.class));

    }

    @Test
    public void getAllPharmacies_returnsPharmacies() throws Exception {

        when(pharmacyService.findAll()).thenReturn(pharmacyList);
        mockMvc.perform(MockMvcRequestBuilders.get(URI).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(pharmacyService, times(1)).findAll();

    }

    @Test
    public void DeleteById_ShouldDeletePharmacy() throws Exception {

        doNothing().when(pharmacyService).deleteById(pharmacy.getId());
        mockMvc.perform(delete(URI + "/" + pharmacy.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testedResourceNotFoundException() throws Exception {

        final Long pharmacyId = 3L;
        when(pharmacyService.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(get(URI + "/{id}", 300L))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void GetMappingOfPharmacy_ShouldReturnRespectivePharmacy() throws Exception {

        when(pharmacyService.findById(pharmacy2.getId())).thenReturn(Optional.ofNullable(pharmacy2));
        mockMvc.perform(get(URI + "/" + pharmacy2.getId()).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name", equalTo(pharmacy2.getName())))
                .andExpect(jsonPath("$.medicineLinkTemplate", equalTo(pharmacy2.getMedicineLinkTemplate())));

    }

    @Test
    public void putMappingOfPharmacy_pharmacyIsUpdated() throws Exception {

        when(pharmacyService.update(anyLong(), any(PharmacyDto.class))).thenReturn(Optional.ofNullable(pharmacy));
        mockMvc.perform(put(URI + "/" + pharmacy.getId()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(pharmacy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(pharmacy.getName()))
                .andExpect(jsonPath("$.medicineLinkTemplate").value(pharmacy.getMedicineLinkTemplate()));
        verify(pharmacyService, times(1)).update(anyLong(), any(PharmacyDto.class));

    }

    @Test
    public void update_testResourceNotFoundException() throws Exception {
        when(pharmacyService.update(anyLong(), any(PharmacyDto.class))).thenReturn(Optional.empty());
        mockMvc.perform(put(URI + "/{id}", pharmacy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pharmacy)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}
