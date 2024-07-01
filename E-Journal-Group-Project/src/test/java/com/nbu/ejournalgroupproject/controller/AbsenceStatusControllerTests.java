package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.AbsenceStatusDTO;
import com.nbu.ejournalgroupproject.enums.AbsenceStatusEnum;
import com.nbu.ejournalgroupproject.service.AbsenceStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(AbsenceStatusController.class)
public class AbsenceStatusControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbsenceStatusService absenceStatusService;

    private AbsenceStatusDTO absenceStatusDTO;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        absenceStatusDTO = new AbsenceStatusDTO();
        absenceStatusDTO.setId(1L);
        absenceStatusDTO.setAbsenceStatusEnum(AbsenceStatusEnum.EXCUSED);

        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllAbsenceStatuses() throws Exception {
        when(absenceStatusService.getAllAbsenceStatuses()).thenReturn(Collections.singletonList(absenceStatusDTO));

        mockMvc.perform(get("/absenceStatuses/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].absenceStatusEnum").value("EXCUSED"));

        verify(absenceStatusService, times(1)).getAllAbsenceStatuses();
    }

    @Test
    void testGetAbsenceStatusById() throws Exception {
        when(absenceStatusService.getAbsenceStatusById(1L)).thenReturn(absenceStatusDTO);

        mockMvc.perform(get("/absenceStatuses/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.absenceStatusEnum").value("EXCUSED"));

        verify(absenceStatusService, times(1)).getAbsenceStatusById(1L);
    }

    @Test
    void testCreateAbsenceStatus() throws Exception {
        when(absenceStatusService.createAbsenceStatus(any(AbsenceStatusDTO.class))).thenReturn(absenceStatusDTO);

        mockMvc.perform(post("/absenceStatuses/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(absenceStatusDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.absenceStatusEnum").value("EXCUSED"));

        verify(absenceStatusService, times(1)).createAbsenceStatus(any(AbsenceStatusDTO.class));
    }

    @Test
    void testDeleteAbsenceStatus() throws Exception {
        doNothing().when(absenceStatusService).deleteAbsenceStatus(1L);

        mockMvc.perform(delete("/absenceStatuses/1"))
                .andExpect(status().isOk());

        verify(absenceStatusService, times(1)).deleteAbsenceStatus(1L);
    }

    @Test
    void testUpdateAbsenceStatus() throws Exception {
        when(absenceStatusService.updateAbsenceStatus(eq(1L), any(AbsenceStatusDTO.class))).thenReturn(absenceStatusDTO);

        mockMvc.perform(put("/absenceStatuses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(absenceStatusDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.absenceStatusEnum").value("EXCUSED"));

        verify(absenceStatusService, times(1)).updateAbsenceStatus(eq(1L), any(AbsenceStatusDTO.class));
    }

    @Test
    void testCreateAbsenceStatusValidationFailure() throws Exception {
        AbsenceStatusDTO invalidAbsenceStatusDTO = new AbsenceStatusDTO();
        invalidAbsenceStatusDTO.setId(1L);
        invalidAbsenceStatusDTO.setAbsenceStatusEnum(null); // Invalid absence status enum

        mockMvc.perform(post("/absenceStatuses/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAbsenceStatusDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.absenceStatusEnum").value("Absence status enum cannot be null"));
    }

    @Test
    void testUpdateAbsenceStatusValidationFailure() throws Exception {
        AbsenceStatusDTO invalidAbsenceStatusDTO = new AbsenceStatusDTO();
        invalidAbsenceStatusDTO.setId(1L);
        invalidAbsenceStatusDTO.setAbsenceStatusEnum(null); // Invalid absence status enum

        mockMvc.perform(put("/absenceStatuses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAbsenceStatusDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.absenceStatusEnum").value("Absence status enum cannot be null"));
    }
}