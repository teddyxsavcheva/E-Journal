package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.HeadmasterDTO;
import com.nbu.ejournalgroupproject.service.GradeService;
import com.nbu.ejournalgroupproject.service.HeadmasterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(HeadmasterController.class)
public class HeadmasterControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeadmasterService headmasterService;

    @MockBean
    private GradeService gradeService;

    private HeadmasterDTO headmasterDTO;

    @BeforeEach
    void setUp() {
        headmasterDTO = new HeadmasterDTO();
        headmasterDTO.setId(1L);
        headmasterDTO.setName("John Doe");
        headmasterDTO.setEmail("john.doe@example.com");
        headmasterDTO.setSchoolId(1L);
    }

    @Test
    void testGetHeadmasters() throws Exception {
        when(headmasterService.getHeadmasters()).thenReturn(Collections.singletonList(headmasterDTO));

        mockMvc.perform(get("/headmaster/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(headmasterService, times(1)).getHeadmasters();
    }

    @Test
    void testGetHeadmasterById() throws Exception {
        when(headmasterService.getHeadmaster(1L)).thenReturn(headmasterDTO);

        mockMvc.perform(get("/headmaster/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(headmasterService, times(1)).getHeadmaster(1L);
    }

    @Test
    void testGetHeadmasterBySchoolId() throws Exception {
        when(headmasterService.getHeadmasterBySchoolID(1L)).thenReturn(headmasterDTO);

        mockMvc.perform(get("/headmaster/school-id/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(headmasterService, times(1)).getHeadmasterBySchoolID(1L);
    }

    @Test
    void testCreateHeadmaster() throws Exception {
        when(headmasterService.createHeadmaster(any(HeadmasterDTO.class))).thenReturn(headmasterDTO);

        mockMvc.perform(post("/headmaster/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(headmasterDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(headmasterService, times(1)).createHeadmaster(any(HeadmasterDTO.class));
    }

    @Test
    void testUpdateHeadmaster() throws Exception {
        when(headmasterService.updateHeadmaster(eq(1L), any(HeadmasterDTO.class))).thenReturn(headmasterDTO);

        mockMvc.perform(put("/headmaster/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(headmasterDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(headmasterService, times(1)).updateHeadmaster(eq(1L), any(HeadmasterDTO.class));
    }

    @Test
    void testDeleteHeadmaster() throws Exception {
        doNothing().when(headmasterService).deleteHeadmaster(1L);

        mockMvc.perform(delete("/headmaster/1"))
                .andExpect(status().isOk());

        verify(headmasterService, times(1)).deleteHeadmaster(1L);
    }

    @Test
    void testCreateHeadmasterValidationFailure() throws Exception {
        HeadmasterDTO invalidHeadmasterDTO = new HeadmasterDTO();
        invalidHeadmasterDTO.setId(1L);
        invalidHeadmasterDTO.setName("J");
        invalidHeadmasterDTO.setEmail("invalid-email");
        invalidHeadmasterDTO.setSchoolId(null);

        mockMvc.perform(post("/headmaster/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidHeadmasterDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 100 characters"))
                .andExpect(jsonPath("$.email").value("Email should be valid"))
                .andExpect(jsonPath("$.schoolId").value("School ID cannot be null"));
    }

    @Test
    void testAvgGradeForSchool() throws Exception {
        when(gradeService.findAvgGradeForSchool(1L)).thenReturn(Collections.singletonList("A"));

        mockMvc.perform(get("/headmaster/1/avg-grade-for-school"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("A"));

        verify(gradeService, times(1)).findAvgGradeForSchool(1L);
    }

    @Test
    void testAvgGradeForDiscipline() throws Exception {
        when(gradeService.findAvgGradeForDiscipline(1L)).thenReturn(Collections.singletonList("B"));

        mockMvc.perform(get("/headmaster/1/avg-grade-for-discipline"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("B"));

        verify(gradeService, times(1)).findAvgGradeForDiscipline(1L);
    }

    @Test
    void testAvgGradeForTeacherByDiscipline() throws Exception {
        when(gradeService.findAvgGradeForTeacherByDiscipline(1L)).thenReturn(Collections.singletonList("C"));

        mockMvc.perform(get("/headmaster/1/avg-grade-for-teacher-by-discipline"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("C"));

        verify(gradeService, times(1)).findAvgGradeForTeacherByDiscipline(1L);
    }
}