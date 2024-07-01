package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;
import com.nbu.ejournalgroupproject.service.SchoolClassService;
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
@WebMvcTest(SchoolClassController.class)
public class SchoolClassControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchoolClassService schoolClassService;

    private SchoolClassDTO schoolClassDTO;

    @BeforeEach
    void setUp() {
        schoolClassDTO = new SchoolClassDTO();
        schoolClassDTO.setId(1L);
        schoolClassDTO.setName("Class 1");
        schoolClassDTO.setYear(2021);
        schoolClassDTO.setSchoolId(1L);
    }

    @Test
    void testGetSchoolClasses() throws Exception {
        when(schoolClassService.getSchoolClasses()).thenReturn(Collections.singletonList(schoolClassDTO));

        mockMvc.perform(get("/school-class/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Class 1"));

        verify(schoolClassService, times(1)).getSchoolClasses();
    }

    @Test
    void testGetSchoolClassById() throws Exception {
        when(schoolClassService.getSchoolClas(1L)).thenReturn(schoolClassDTO);

        mockMvc.perform(get("/school-class/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Class 1"));

        verify(schoolClassService, times(1)).getSchoolClas(1L);
    }

    @Test
    void testGetSchoolClassBySchoolId() throws Exception {
        when(schoolClassService.getSchoolClasBySchoolId(1L)).thenReturn(Collections.singletonList(schoolClassDTO));

        mockMvc.perform(get("/school-class/school-id/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Class 1"));

        verify(schoolClassService, times(1)).getSchoolClasBySchoolId(1L);
    }

    @Test
    void testCreateSchoolClass() throws Exception {
        when(schoolClassService.createSchoolClass(any(SchoolClassDTO.class))).thenReturn(schoolClassDTO);

        mockMvc.perform(post("/school-class/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(schoolClassDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Class 1"));

        verify(schoolClassService, times(1)).createSchoolClass(any(SchoolClassDTO.class));
    }

    @Test
    void testCreateSchoolClassValidationFailure() throws Exception {
        SchoolClassDTO invalidSchoolClassDTO = new SchoolClassDTO();
        invalidSchoolClassDTO.setId(1L);
        invalidSchoolClassDTO.setName("Class 1");
        invalidSchoolClassDTO.setYear(0);  // Invalid year
        invalidSchoolClassDTO.setSchoolId(1L);

        mockMvc.perform(post("/school-class/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidSchoolClassDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.year").value("Year must be greater than or equal to 2000"));
    }

    @Test
    void testUpdateSchoolClass() throws Exception {
        when(schoolClassService.updateSchoolClass(eq(1L), any(SchoolClassDTO.class))).thenReturn(schoolClassDTO);

        mockMvc.perform(put("/school-class/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(schoolClassDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Class 1"));

        verify(schoolClassService, times(1)).updateSchoolClass(eq(1L), any(SchoolClassDTO.class));
    }

    @Test
    void testUpdateSchoolClassValidationFailure() throws Exception {
        SchoolClassDTO invalidSchoolClassDTO = new SchoolClassDTO();
        invalidSchoolClassDTO.setId(1L);
        invalidSchoolClassDTO.setName("Class 1");
        invalidSchoolClassDTO.setYear(0);  // Invalid year
        invalidSchoolClassDTO.setSchoolId(1L);

        mockMvc.perform(put("/school-class/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidSchoolClassDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.year").value("Year must be greater than or equal to 2000"));
    }

    @Test
    void testDeleteSchoolClass() throws Exception {
        doNothing().when(schoolClassService).deleteSchoolClass(1L);

        mockMvc.perform(delete("/school-class/1"))
                .andExpect(status().isOk());

        verify(schoolClassService, times(1)).deleteSchoolClass(1L);
    }

    @Test
    void testGetSchoolClassByTeacherId() throws Exception {
        when(schoolClassService.getClassesFromTeacherId(1L)).thenReturn(Collections.singletonList(schoolClassDTO));

        mockMvc.perform(get("/school-class/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Class 1"));

        verify(schoolClassService, times(1)).getClassesFromTeacherId(1L);
    }
}
