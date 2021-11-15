package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.services.impl.MedicineServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = MedicineController.class)
public class MedicineControllerTest {

    private final String URI = "/medicines";

    @MockBean
    private MedicineServiceImpl medicineService;
    private Medicine medicine;
    private Medicine medicine2;
    private List<Medicine> medicineList;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private MedicineController medicineController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        medicine = new Medicine(8L, "Vitamin C");
        medicine2 = new Medicine(9L, "Vitamin B");
        medicineList = Arrays.asList(medicine, medicine2);

    }

    @AfterEach
    void tearDown() {

        medicine = null;
        medicine2 = null;
        medicineList = null;

    }

    @Test
    public void postMappingOfMedicine_medicineIsCreated() throws Exception {

        when(medicineService.save(any(MedicineDto.class))).thenReturn(medicine);
        mockMvc.perform(post(URI).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(medicine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(medicine.getTitle()));
        verify(medicineService, times(1)).save(any(MedicineDto.class));

    }

    @Test
    public void getAllProducts_() throws Exception {

        when(medicineService.findAll()).thenReturn(medicineList);
        mockMvc.perform(MockMvcRequestBuilders.get(URI).
                        contentType(MediaType.APPLICATION_JSON)).
                andDo(MockMvcResultHandlers.print());
        verify(medicineService, times(1)).findAll();

    }

    @Test
    public void DeleteById_ShouldDeleteMedicine() throws Exception {

        doNothing().when(medicineService).delete(medicine.getId());
        mockMvc.perform(delete(URI + "/" + medicine.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).
                andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void GetMappingOfMedicine_ShouldReturnRespectiveMedicine() throws Exception {

        when(medicineService.findById(medicine.getId())).thenReturn(Optional.ofNullable(medicine));
        mockMvc.perform(get(URI + "/" + medicine.getId()).
                        contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.title", equalTo(medicine.getTitle())));

    }

    @Test
    public void putMappingOfMedicine_medicineIsUpdated() throws Exception {

        when(medicineService.update(anyLong(), any(MedicineDto.class))).thenReturn(Optional.ofNullable(medicine));
        mockMvc.perform(put(URI + "/" + medicine.getId()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(medicine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(medicine.getTitle()));
        verify(medicineService, times(1)).update(anyLong(), any(MedicineDto.class));

    }

    @Test
    public void findById_testResourceNotFoundException() throws Exception {
        when(medicineService.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(get(URI + "/{id}", 1000L))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_testResourceNotFoundException() throws Exception {
        when(medicineService.update(anyLong(), any(MedicineDto.class))).thenReturn(Optional.empty());
        mockMvc.perform(put(URI + "/{id}", 1000L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medicine)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}
