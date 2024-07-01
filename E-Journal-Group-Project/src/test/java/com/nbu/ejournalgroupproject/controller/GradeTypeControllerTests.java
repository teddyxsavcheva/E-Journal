package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.GradeTypeDTO;
import com.nbu.ejournalgroupproject.enums.GradeTypeEnum;
import com.nbu.ejournalgroupproject.service.GradeTypeService;
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
@WebMvcTest(GradeTypeController.class)
public class GradeTypeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GradeTypeService gradeTypeService;

    private GradeTypeDTO gradeTypeDTO;

    @BeforeEach
    void setUp() {
        gradeTypeDTO = new GradeTypeDTO();
        gradeTypeDTO.setId(1L);
        gradeTypeDTO.setGradeTypeEnum(GradeTypeEnum.A);
    }

    @Test
    void testGetAllGradeTypes() throws Exception {
        when(gradeTypeService.getAllGradeTypes()).thenReturn(Collections.singletonList(gradeTypeDTO));

        mockMvc.perform(get("/gradeTypes/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].gradeTypeEnum").value("A"));

        verify(gradeTypeService, times(1)).getAllGradeTypes();
    }

    @Test
    void testGetGradeTypeById() throws Exception {
        when(gradeTypeService.getGradeTypeById(1L)).thenReturn(gradeTypeDTO);

        mockMvc.perform(get("/gradeTypes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gradeTypeEnum").value("A"));

        verify(gradeTypeService, times(1)).getGradeTypeById(1L);
    }

    @Test
    void testCreateGradeType() throws Exception {
        when(gradeTypeService.createGradeType(any(GradeTypeDTO.class))).thenReturn(gradeTypeDTO);

        mockMvc.perform(post("/gradeTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(gradeTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gradeTypeEnum").value("A"));

        verify(gradeTypeService, times(1)).createGradeType(any(GradeTypeDTO.class));
    }

    @Test
    void testDeleteGradeType() throws Exception {
        doNothing().when(gradeTypeService).deleteGradeType(1L);

        mockMvc.perform(delete("/gradeTypes/1"))
                .andExpect(status().isOk());

        verify(gradeTypeService, times(1)).deleteGradeType(1L);
    }

    @Test
    void testUpdateGradeType() throws Exception {
        when(gradeTypeService.updateGradeType(eq(1L), any(GradeTypeDTO.class))).thenReturn(gradeTypeDTO);

        mockMvc.perform(put("/gradeTypes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(gradeTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gradeTypeEnum").value("A"));

        verify(gradeTypeService, times(1)).updateGradeType(eq(1L), any(GradeTypeDTO.class));
    }

    @Test
    void testCreateGradeTypeValidationFailure() throws Exception {
        GradeTypeDTO invalidGradeTypeDTO = new GradeTypeDTO();
        invalidGradeTypeDTO.setId(1L);
        invalidGradeTypeDTO.setGradeTypeEnum(null); // Invalid enum

        mockMvc.perform(post("/gradeTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidGradeTypeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gradeTypeEnum").value("Grade type enum cannot be null"));
    }

    @Test
    void testUpdateGradeTypeValidationFailure() throws Exception {
        GradeTypeDTO invalidGradeTypeDTO = new GradeTypeDTO();
        invalidGradeTypeDTO.setId(1L);
        invalidGradeTypeDTO.setGradeTypeEnum(null); // Invalid enum

        mockMvc.perform(put("/gradeTypes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidGradeTypeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.gradeTypeEnum").value("Grade type enum cannot be null"));
    }
}