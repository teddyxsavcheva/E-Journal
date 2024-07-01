package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.SchoolDTO;
import com.nbu.ejournalgroupproject.service.SchoolService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SchoolController.class)
public class SchoolControllerTests{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchoolService schoolService;

    private SchoolDTO schoolDTO;

    @BeforeEach
    void setUp() {
        schoolDTO = new SchoolDTO();
        schoolDTO.setId(1L);
        schoolDTO.setName("Test School");
        schoolDTO.setAddress("123 Test Address");
        schoolDTO.setSchoolTypeId(1L);
    }

    @Test
    void testGetSchools() throws Exception {
        when(schoolService.getSchools()).thenReturn(Collections.singletonList(schoolDTO));

        mockMvc.perform(get("/school/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Test School"));

        verify(schoolService, times(1)).getSchools();
    }

    @Test
    void testGetSchoolById() throws Exception {
        when(schoolService.getSchool(1L)).thenReturn(schoolDTO);

        mockMvc.perform(get("/school/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test School"));

        verify(schoolService, times(1)).getSchool(1L);
    }

    @Test
    void testCreateSchool() throws Exception {
        when(schoolService.createSchool(any(SchoolDTO.class))).thenReturn(schoolDTO);

        mockMvc.perform(post("/school/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(schoolDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test School"));

        verify(schoolService, times(1)).createSchool(any(SchoolDTO.class));
    }

    @Test
    void testUpdateSchool() throws Exception {
        when(schoolService.updateSchool(eq(1L), any(SchoolDTO.class))).thenReturn(schoolDTO);

        mockMvc.perform(put("/school/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(schoolDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test School"));

        verify(schoolService, times(1)).updateSchool(eq(1L), any(SchoolDTO.class));
    }

    @Test
    void testDeleteSchool() throws Exception {
        doNothing().when(schoolService).deleteSchool(1L);

        mockMvc.perform(delete("/school/1"))
                .andExpect(status().isOk());

        verify(schoolService, times(1)).deleteSchool(1L);
    }

    @Test
    void testCreateSchoolValidationFailure() throws Exception {
        SchoolDTO invalidSchoolDTO = new SchoolDTO();
        invalidSchoolDTO.setId(1L);
        invalidSchoolDTO.setName("T");
        invalidSchoolDTO.setAddress("");
        invalidSchoolDTO.setSchoolTypeId(null);

        mockMvc.perform(post("/school/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidSchoolDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 200 characters"))
                .andExpect(jsonPath("$.address").value("Address must not be blank"))
                .andExpect(jsonPath("$.schoolTypeId").value("School Type ID cannot be null"));
    }
}