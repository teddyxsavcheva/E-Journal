package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.AbsenceTypeDTO;
import com.nbu.ejournalgroupproject.enums.AbsenceTypeEnum;
import com.nbu.ejournalgroupproject.service.AbsenceTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
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

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AbsenceTypeController.class)
public class AbsenceTypeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AbsenceTypeService absenceTypeService;

    private AbsenceTypeDTO absenceTypeDTO;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        absenceTypeDTO = new AbsenceTypeDTO();
        absenceTypeDTO.setId(1L);
        absenceTypeDTO.setAbsenceTypeEnum(AbsenceTypeEnum.SICK_LEAVE);

        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllAbsenceTypes() throws Exception {
        when(absenceTypeService.getAllAbsenceTypes()).thenReturn(Collections.singletonList(absenceTypeDTO));

        mockMvc.perform(get("/absenceTypes/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].absenceTypeEnum").value("SICK_LEAVE"));

        verify(absenceTypeService, times(1)).getAllAbsenceTypes();
    }

    @Test
    void testGetAbsenceTypeById() throws Exception {
        when(absenceTypeService.getAbsenceTypeById(1L)).thenReturn(absenceTypeDTO);

        mockMvc.perform(get("/absenceTypes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.absenceTypeEnum").value("SICK_LEAVE"));

        verify(absenceTypeService, times(1)).getAbsenceTypeById(1L);
    }

    @Test
    void testCreateAbsenceType() throws Exception {
        when(absenceTypeService.createAbsenceType(any(AbsenceTypeDTO.class))).thenReturn(absenceTypeDTO);

        mockMvc.perform(post("/absenceTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(absenceTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.absenceTypeEnum").value("SICK_LEAVE"));

        verify(absenceTypeService, times(1)).createAbsenceType(any(AbsenceTypeDTO.class));
    }

    @Test
    void testDeleteAbsenceType() throws Exception {
        doNothing().when(absenceTypeService).deleteAbsenceType(1L);

        mockMvc.perform(delete("/absenceTypes/1"))
                .andExpect(status().isOk());

        verify(absenceTypeService, times(1)).deleteAbsenceType(1L);
    }

    @Test
    void testUpdateAbsenceType() throws Exception {
        when(absenceTypeService.updateAbsenceType(eq(1L), any(AbsenceTypeDTO.class))).thenReturn(absenceTypeDTO);

        mockMvc.perform(put("/absenceTypes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(absenceTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.absenceTypeEnum").value("SICK_LEAVE"));

        verify(absenceTypeService, times(1)).updateAbsenceType(eq(1L), any(AbsenceTypeDTO.class));
    }

    @Test
    void testCreateAbsenceTypeValidationFailure() throws Exception {
        AbsenceTypeDTO invalidAbsenceTypeDTO = new AbsenceTypeDTO();
        invalidAbsenceTypeDTO.setId(1L);
        invalidAbsenceTypeDTO.setAbsenceTypeEnum(null); // Invalid absence type enum

        mockMvc.perform(post("/absenceTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAbsenceTypeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.absenceTypeEnum").value("Absence type enum cannot be null"));
    }

    @Test
    void testUpdateAbsenceTypeValidationFailure() throws Exception {
        AbsenceTypeDTO invalidAbsenceTypeDTO = new AbsenceTypeDTO();
        invalidAbsenceTypeDTO.setId(1L);
        invalidAbsenceTypeDTO.setAbsenceTypeEnum(null); // Invalid absence type enum

        mockMvc.perform(put("/absenceTypes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAbsenceTypeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.absenceTypeEnum").value("Absence type enum cannot be null"));
    }
}