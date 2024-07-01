package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nbu.ejournalgroupproject.dto.AbsenceDTO;
import com.nbu.ejournalgroupproject.service.AbsenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AbsenceController.class)
public class AbsenceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbsenceService absenceService;

    private AbsenceDTO absenceDTO;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        absenceDTO = new AbsenceDTO();
        absenceDTO.setId(1L);
        absenceDTO.setDateOfIssue(LocalDate.now());
        absenceDTO.setStudentId(1L);
        absenceDTO.setDisciplineId(1L);
        absenceDTO.setAbsenceTypeId(1L);
        absenceDTO.setAbsenceStatusId(1L);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register module to handle Java 8 date/time types
    }

    @Test
    void testGetAllAbsences() throws Exception {
        when(absenceService.getAllAbsences()).thenReturn(Collections.singletonList(absenceDTO));

        mockMvc.perform(get("/absences/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].studentId").value(1L));

        verify(absenceService, times(1)).getAllAbsences();
    }

    @Test
    void testGetAbsenceById() throws Exception {
        when(absenceService.getAbsenceById(1L)).thenReturn(absenceDTO);

        mockMvc.perform(get("/absences/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(1L));

        verify(absenceService, times(1)).getAbsenceById(1L);
    }

    @Test
    void testCreateAbsence() throws Exception {
        when(absenceService.createAbsence(any(AbsenceDTO.class))).thenReturn(absenceDTO);

        mockMvc.perform(post("/absences/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(absenceDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(1L));

        verify(absenceService, times(1)).createAbsence(any(AbsenceDTO.class));
    }

    @Test
    void testDeleteAbsence() throws Exception {
        doNothing().when(absenceService).deleteAbsence(1L);

        mockMvc.perform(delete("/absences/1"))
                .andExpect(status().isOk());

        verify(absenceService, times(1)).deleteAbsence(1L);
    }

    @Test
    void testUpdateAbsence() throws Exception {
        when(absenceService.updateAbsence(eq(1L), any(AbsenceDTO.class))).thenReturn(absenceDTO);

        mockMvc.perform(put("/absences/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(absenceDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(1L));

        verify(absenceService, times(1)).updateAbsence(eq(1L), any(AbsenceDTO.class));
    }

    @Test
    void testCreateAbsenceValidationFailure() throws Exception {
        AbsenceDTO invalidAbsenceDTO = new AbsenceDTO();
        invalidAbsenceDTO.setId(1L);
        invalidAbsenceDTO.setDateOfIssue(LocalDate.now());
        invalidAbsenceDTO.setStudentId(null); // Invalid student ID
        invalidAbsenceDTO.setDisciplineId(1L);
        invalidAbsenceDTO.setAbsenceTypeId(1L);
        invalidAbsenceDTO.setAbsenceStatusId(1L);

        mockMvc.perform(post("/absences/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAbsenceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value("Student ID cannot be null"));
    }

    @Test
    void testUpdateAbsenceValidationFailure() throws Exception {
        AbsenceDTO invalidAbsenceDTO = new AbsenceDTO();
        invalidAbsenceDTO.setId(1L);
        invalidAbsenceDTO.setDateOfIssue(LocalDate.now());
        invalidAbsenceDTO.setStudentId(null); // Invalid student ID
        invalidAbsenceDTO.setDisciplineId(1L);
        invalidAbsenceDTO.setAbsenceTypeId(1L);
        invalidAbsenceDTO.setAbsenceStatusId(1L);

        mockMvc.perform(put("/absences/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAbsenceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value("Student ID cannot be null"));
    }
}