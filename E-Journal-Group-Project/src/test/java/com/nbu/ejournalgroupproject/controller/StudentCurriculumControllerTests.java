package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.StudentCurriculumDTO;
import com.nbu.ejournalgroupproject.service.StudentCurriculumService;
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
@WebMvcTest(StudentCurriculumController.class)
public class StudentCurriculumControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentCurriculumService studentCurriculumService;

    private StudentCurriculumDTO studentCurriculumDTO;

    @BeforeEach
    void setUp() {
        studentCurriculumDTO = new StudentCurriculumDTO();
        studentCurriculumDTO.setId(1L);
        studentCurriculumDTO.setSemester(1);
        studentCurriculumDTO.setYear(5);
        studentCurriculumDTO.setSchoolClassId(1L);
    }

    @Test
    void testGetStudentCurriculums() throws Exception {
        when(studentCurriculumService.getStudentCurriculums()).thenReturn(Collections.singletonList(studentCurriculumDTO));

        mockMvc.perform(get("/student-curriculum/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].semester").value(1))
                .andExpect(jsonPath("$[0].year").value(5))
                .andExpect(jsonPath("$[0].schoolClassId").value(1L));

        verify(studentCurriculumService, times(1)).getStudentCurriculums();
    }

    @Test
    void testGetStudentCurriculumById() throws Exception {
        when(studentCurriculumService.getStudentCurriculum(1L)).thenReturn(studentCurriculumDTO);

        mockMvc.perform(get("/student-curriculum/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.semester").value(1))
                .andExpect(jsonPath("$.year").value(5))
                .andExpect(jsonPath("$.schoolClassId").value(1L));

        verify(studentCurriculumService, times(1)).getStudentCurriculum(1L);
    }

    @Test
    void testCreateStudentCurriculum() throws Exception {
        when(studentCurriculumService.createStudentCurriculum(any(StudentCurriculumDTO.class))).thenReturn(studentCurriculumDTO);

        mockMvc.perform(post("/student-curriculum/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(studentCurriculumDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.semester").value(1))
                .andExpect(jsonPath("$.year").value(5))
                .andExpect(jsonPath("$.schoolClassId").value(1L));

        verify(studentCurriculumService, times(1)).createStudentCurriculum(any(StudentCurriculumDTO.class));
    }

    @Test
    void testCreateStudentCurriculumValidationFailure() throws Exception {
        StudentCurriculumDTO invalidStudentCurriculumDTO = new StudentCurriculumDTO();
        invalidStudentCurriculumDTO.setId(1L);
        invalidStudentCurriculumDTO.setSemester(0);  // Invalid semester
        invalidStudentCurriculumDTO.setYear(13);  // Invalid year
        invalidStudentCurriculumDTO.setSchoolClassId(null);  // Invalid schoolClassId

        mockMvc.perform(post("/student-curriculum/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidStudentCurriculumDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.semester").value("Semester must be at least 1"))
                .andExpect(jsonPath("$.year").value("Year must be at most 12"))
                .andExpect(jsonPath("$.schoolClassId").value("School class cannot be null"));
    }

    @Test
    void testUpdateStudentCurriculum() throws Exception {
        when(studentCurriculumService.updateStudentCurriculum(eq(1L), any(StudentCurriculumDTO.class))).thenReturn(studentCurriculumDTO);

        mockMvc.perform(put("/student-curriculum/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(studentCurriculumDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.semester").value(1))
                .andExpect(jsonPath("$.year").value(5))
                .andExpect(jsonPath("$.schoolClassId").value(1L));

        verify(studentCurriculumService, times(1)).updateStudentCurriculum(eq(1L), any(StudentCurriculumDTO.class));
    }

    @Test
    void testUpdateStudentCurriculumValidationFailure() throws Exception {
        StudentCurriculumDTO invalidStudentCurriculumDTO = new StudentCurriculumDTO();
        invalidStudentCurriculumDTO.setId(1L);
        invalidStudentCurriculumDTO.setSemester(0);  // Invalid semester
        invalidStudentCurriculumDTO.setYear(13);  // Invalid year
        invalidStudentCurriculumDTO.setSchoolClassId(null);  // Invalid schoolClassId

        mockMvc.perform(put("/student-curriculum/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidStudentCurriculumDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.semester").value("Semester must be at least 1"))
                .andExpect(jsonPath("$.year").value("Year must be at most 12"))
                .andExpect(jsonPath("$.schoolClassId").value("School class cannot be null"));
    }

    @Test
    void testDeleteStudentCurriculum() throws Exception {
        doNothing().when(studentCurriculumService).deleteStudentCurriculum(1L);

        mockMvc.perform(delete("/student-curriculum/1"))
                .andExpect(status().isOk());

        verify(studentCurriculumService, times(1)).deleteStudentCurriculum(1L);
    }

    @Test
    void testGetStudentCurriculumByClassId() throws Exception {
        when(studentCurriculumService.getStudentCurriculumByClassId(1L)).thenReturn(studentCurriculumDTO);

        mockMvc.perform(get("/student-curriculum/school-class/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.semester").value(1))
                .andExpect(jsonPath("$.year").value(5))
                .andExpect(jsonPath("$.schoolClassId").value(1L));

        verify(studentCurriculumService, times(1)).getStudentCurriculumByClassId(1L);
    }
}