package com.example.storage.controllers;

import com.example.storage.models.Supply;
import com.example.storage.services.SupplyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.print.attribute.standard.MediaSize;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@WebMvcTest(SupplyController.class)
public class SupplyControllerTest {
    @MockBean
    private SupplyService supplyService;

    @InjectMocks
    private SupplyController supplyController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getSupplyTest() throws Exception {
        Supply supply = new Supply(1L, "Cup", null);
        when(supplyService.getSupplyById(supply.getId())).thenReturn(supply);
        mockMvc.perform(get("/supplies/{id}", 1L))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.name").value("Cup"),
                        jsonPath("$.username").isEmpty());
        verify(supplyService, times(1)).getSupplyById(1L);
    }

    @Test
    public void getFirstFreeSupplyByNameTest() throws Exception {
        Supply supply = new Supply(1L, "Cup", null);
        when(supplyService.getFirstFreeSupplyByName("Cup")).thenReturn(supply);
        mockMvc.perform(get("/supplies/{name}/free","Cup"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(("$.name")).value("Cup"))
                .andExpect(jsonPath("$.username").isEmpty());
        verify(supplyService, times(1)).getFirstFreeSupplyByName("Cup");
    }

    @Test
    public void getCountFreeSupplyTest() throws Exception {
        Supply supply = new Supply(1L, "Cup", null);
        when(supplyService.getCountFreeSupply("Cup")).thenReturn(1);
        mockMvc.perform(get("/supplies/{name}/count","Cup"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
        verify(supplyService, times(1)).getCountFreeSupply("Cup");
    }

    @Test
    public void createSupplyTest() throws Exception {
        Supply supply = new Supply(1L, "Cup", null);
        String json = objectMapper.writeValueAsString(supply);
        when(supplyService.createSupply(supply)).thenReturn(supply);
        mockMvc.perform(post("/supplies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
        verify(supplyService, times(1)).createSupply(supply);
    }

    @Test
    public void updateSupplyTest() throws Exception {
        Supply supply = new Supply(1L,"Cup","qwe");
        String json = objectMapper.writeValueAsString(supply);
        when(supplyService.updateSupply(supply)).thenReturn(supply);
        mockMvc.perform(put("/supplies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        verify(supplyService, times(1)).updateSupply(supply);
    }

    @Test
    public void deleteSupplyTest() throws Exception {
        Supply supply = new Supply(1L,"Cup","qwe");
        mockMvc.perform(delete("/supplies/{id}", 1L))
                .andExpect(status().isNoContent());
        verify(supplyService, times(1)).deleteSupplyById(1L);
    }

    @Test
    public void getAllSuppliesTest() throws Exception {
        List<Supply> supplies = List.of(new Supply(1L,"Cup","qwe"));
        mockMvc.perform(get("/supplies"))
                .andExpect(status().isOk());
        verify(supplyService, times(1)).getAllSupplies();
    }
}

