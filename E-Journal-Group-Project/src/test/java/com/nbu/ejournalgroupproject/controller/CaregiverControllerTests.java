package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.CaregiverDTO;
import com.nbu.ejournalgroupproject.service.CaregiverService;
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
@WebMvcTest(CaregiverController.class)
public class CaregiverControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaregiverService caregiverService;

    private CaregiverDTO caregiverDTO;

    @BeforeEach
    void setUp() {
        caregiverDTO = new CaregiverDTO();
        caregiverDTO.setId(1L);
        caregiverDTO.setName("Jane Doe");
        caregiverDTO.setEmail("jane.doe@example.com"); // Set a valid email for successful tests
    }

    @Test
    void testGetAllCaregivers() throws Exception {
        when(caregiverService.getAllCaregivers()).thenReturn(Collections.singletonList(caregiverDTO));

        mockMvc.perform(get("/caregivers/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Jane Doe"));

        verify(caregiverService, times(1)).getAllCaregivers();
    }

    @Test
    void testGetCaregiverById() throws Exception {
        when(caregiverService.getCaregiverById(1L)).thenReturn(caregiverDTO);

        mockMvc.perform(get("/caregivers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jane Doe"));

        verify(caregiverService, times(1)).getCaregiverById(1L);
    }

    @Test
    void testCreateCaregiver() throws Exception {
        when(caregiverService.createCaregiver(any(CaregiverDTO.class))).thenReturn(caregiverDTO);

        mockMvc.perform(post("/caregivers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(caregiverDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jane Doe"));

        verify(caregiverService, times(1)).createCaregiver(any(CaregiverDTO.class));
    }

    @Test
    void testCreateCaregiverValidationFailure() throws Exception {
        CaregiverDTO invalidCaregiverDTO = new CaregiverDTO();
        invalidCaregiverDTO.setId(1L);
        invalidCaregiverDTO.setName("Jane Doe");
        invalidCaregiverDTO.setEmail(null); // Invalid email

        mockMvc.perform(post("/caregivers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidCaregiverDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("Email cannot be empty"));
    }

    @Test
    void testDeleteCaregiver() throws Exception {
        doNothing().when(caregiverService).deleteCaregiver(1L);

        mockMvc.perform(delete("/caregivers/1"))
                .andExpect(status().isOk());

        verify(caregiverService, times(1)).deleteCaregiver(1L);
    }

    @Test
    void testUpdateCaregiver() throws Exception {
        when(caregiverService.updateCaregiver(eq(1L), any(CaregiverDTO.class))).thenReturn(caregiverDTO);

        mockMvc.perform(put("/caregivers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(caregiverDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jane Doe"));

        verify(caregiverService, times(1)).updateCaregiver(eq(1L), any(CaregiverDTO.class));
    }

    @Test
    void testUpdateCaregiverValidationFailure() throws Exception {
        CaregiverDTO invalidCaregiverDTO = new CaregiverDTO();
        invalidCaregiverDTO.setId(1L);
        invalidCaregiverDTO.setName("Jane Doe");
        invalidCaregiverDTO.setEmail(null); // Invalid email

        mockMvc.perform(put("/caregivers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidCaregiverDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("Email cannot be empty"));
    }

    @Test
    void testAddStudentToCaregiver() throws Exception {
        when(caregiverService.addStudentToCaregiver(1L, 1L)).thenReturn(caregiverDTO);

        mockMvc.perform(post("/caregivers/1/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jane Doe"));

        verify(caregiverService, times(1)).addStudentToCaregiver(1L, 1L);
    }

    @Test
    void testRemoveStudentFromCaregiver() throws Exception {
        when(caregiverService.removeStudentFromCaregiver(1L, 1L)).thenReturn(caregiverDTO);

        mockMvc.perform(delete("/caregivers/1/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Jane Doe"));

        verify(caregiverService, times(1)).removeStudentFromCaregiver(1L, 1L);
    }

    @Test
    void testGetCaregiverByStudentId() throws Exception {
        when(caregiverService.getCaregiversFromStudentId(1L)).thenReturn(Collections.singletonList(caregiverDTO));

        mockMvc.perform(get("/caregivers/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Jane Doe"));

        verify(caregiverService, times(1)).getCaregiversFromStudentId(1L);
    }
}