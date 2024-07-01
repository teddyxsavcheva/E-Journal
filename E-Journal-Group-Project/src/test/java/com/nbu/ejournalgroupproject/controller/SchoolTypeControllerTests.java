package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.SchoolTypeDTO;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.service.SchoolTypeService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(SchoolTypeController.class)
public class SchoolTypeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchoolTypeService schoolTypeService;

    private SchoolTypeDTO schoolTypeDTO;

    @BeforeEach
    void setUp() {
        schoolTypeDTO = new SchoolTypeDTO(1L, SchoolTypeEnum.PRIMARY_SCHOOL);
    }

    @Test
    void testGetSchoolTypes() throws Exception {
        when(schoolTypeService.getAllSchoolTypes()).thenReturn(Collections.singletonList(schoolTypeDTO));

        mockMvc.perform(get("/school-type/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].schoolType").value("PRIMARY_SCHOOL"));

        verify(schoolTypeService, times(1)).getAllSchoolTypes();
    }

    @Test
    void testGetSchoolTypeById() throws Exception {
        when(schoolTypeService.getSchoolTypeById(1L)).thenReturn(schoolTypeDTO);

        mockMvc.perform(get("/school-type/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.schoolType").value("PRIMARY_SCHOOL"));

        verify(schoolTypeService, times(1)).getSchoolTypeById(1L);
    }

    @Test
    void testCreateSchoolType() throws Exception {
        when(schoolTypeService.createSchoolType(any(SchoolTypeDTO.class))).thenReturn(schoolTypeDTO);

        mockMvc.perform(post("/school-type/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(schoolTypeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.schoolType").value("PRIMARY_SCHOOL"));

        verify(schoolTypeService, times(1)).createSchoolType(any(SchoolTypeDTO.class));
    }

    @Test
    void testUpdateSchoolType() throws Exception {
        when(schoolTypeService.updateSchoolType(eq(1L), any(SchoolTypeDTO.class))).thenReturn(schoolTypeDTO);

        mockMvc.perform(put("/school-type/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(schoolTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.schoolType").value("PRIMARY_SCHOOL"));

        verify(schoolTypeService, times(1)).updateSchoolType(eq(1L), any(SchoolTypeDTO.class));
    }

    @Test
    void testDeleteSchoolType() throws Exception {
        doNothing().when(schoolTypeService).deleteSchoolType(1L);

        mockMvc.perform(delete("/school-type/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(schoolTypeService, times(1)).deleteSchoolType(1L);
    }
}