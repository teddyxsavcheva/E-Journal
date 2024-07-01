package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nbu.ejournalgroupproject.dto.GradeDTO;
import com.nbu.ejournalgroupproject.service.GradeService;
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
@WebMvcTest(GradeController.class)
public class GradeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GradeService gradeService;

    private GradeDTO gradeDTO;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        gradeDTO = new GradeDTO();
        gradeDTO.setId(1L);
        gradeDTO.setDateOfIssue(LocalDate.now());
        gradeDTO.setStudentId(1L);
        gradeDTO.setDisciplineId(1L);
        gradeDTO.setGradeTypeId(1L);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testGetAllGrades() throws Exception {
        when(gradeService.getAllGrades()).thenReturn(Collections.singletonList(gradeDTO));

        mockMvc.perform(get("/grades/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].studentId").value(1L));

        verify(gradeService, times(1)).getAllGrades();
    }

    @Test
    void testGetGradeById() throws Exception {
        when(gradeService.getGradeById(1L)).thenReturn(gradeDTO);

        mockMvc.perform(get("/grades/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(1L));

        verify(gradeService, times(1)).getGradeById(1L);
    }

    @Test
    void testCreateGrade() throws Exception {
        when(gradeService.createGrade(any(GradeDTO.class))).thenReturn(gradeDTO);

        mockMvc.perform(post("/grades/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gradeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(1L));

        verify(gradeService, times(1)).createGrade(any(GradeDTO.class));
    }

    @Test
    void testDeleteGrade() throws Exception {
        doNothing().when(gradeService).deleteGrade(1L);

        mockMvc.perform(delete("/grades/1"))
                .andExpect(status().isOk());

        verify(gradeService, times(1)).deleteGrade(1L);
    }

    @Test
    void testUpdateGrade() throws Exception {
        when(gradeService.updateGrade(eq(1L), any(GradeDTO.class))).thenReturn(gradeDTO);

        mockMvc.perform(put("/grades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gradeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value(1L));

        verify(gradeService, times(1)).updateGrade(eq(1L), any(GradeDTO.class));
    }

    @Test
    void testCreateGradeValidationFailure() throws Exception {
        GradeDTO invalidGradeDTO = new GradeDTO();
        invalidGradeDTO.setId(1L);
        invalidGradeDTO.setDateOfIssue(LocalDate.now());
        invalidGradeDTO.setStudentId(null); // Invalid student ID
        invalidGradeDTO.setDisciplineId(1L);
        invalidGradeDTO.setGradeTypeId(1L);

        mockMvc.perform(post("/grades/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidGradeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value("Student ID cannot be null"));
    }

    @Test
    void testUpdateGradeValidationFailure() throws Exception {
        GradeDTO invalidGradeDTO = new GradeDTO();
        invalidGradeDTO.setId(1L);
        invalidGradeDTO.setDateOfIssue(LocalDate.now());
        invalidGradeDTO.setStudentId(null); // Invalid student ID
        invalidGradeDTO.setDisciplineId(1L);
        invalidGradeDTO.setGradeTypeId(1L);

        mockMvc.perform(put("/grades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidGradeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.studentId").value("Student ID cannot be null"));
    }
}