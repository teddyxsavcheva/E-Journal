package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import com.nbu.ejournalgroupproject.service.TeacherService;
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
@WebMvcTest(TeacherController.class)
public class TeacherControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    private TeacherDTO teacherDTO;

    @BeforeEach
    void setUp() {
        teacherDTO = new TeacherDTO();
        teacherDTO.setId(1L);
        teacherDTO.setName("John Doe");
        teacherDTO.setEmail("john.doe@example.com");
        teacherDTO.setSchoolId(1L);
        teacherDTO.setTeacherQualificationIds(Collections.singletonList(1L));
    }

    @Test
    void testGetTeachers() throws Exception {
        when(teacherService.getTeachers()).thenReturn(Collections.singletonList(teacherDTO));

        mockMvc.perform(get("/teacher/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(teacherService, times(1)).getTeachers();
    }

    @Test
    void testGetTeacherById() throws Exception {
        when(teacherService.getTeacherById(1L)).thenReturn(teacherDTO);

        mockMvc.perform(get("/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(teacherService, times(1)).getTeacherById(1L);
    }

    @Test
    void testCreateTeacher() throws Exception {
        when(teacherService.createTeacher(any(TeacherDTO.class))).thenReturn(teacherDTO);

        mockMvc.perform(post("/teacher/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacherDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(teacherService, times(1)).createTeacher(any(TeacherDTO.class));
    }

    @Test
    void testUpdateTeacher() throws Exception {
        when(teacherService.updateTeacher(eq(1L), any(TeacherDTO.class))).thenReturn(teacherDTO);

        mockMvc.perform(put("/teacher/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(teacherDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(teacherService, times(1)).updateTeacher(eq(1L), any(TeacherDTO.class));
    }

    @Test
    void testDeleteTeacher() throws Exception {
        doNothing().when(teacherService).deleteTeacher(1L);

        mockMvc.perform(delete("/teacher/1"))
                .andExpect(status().isOk());

        verify(teacherService, times(1)).deleteTeacher(1L);
    }

    @Test
    void testCreateTeacherValidationFailure() throws Exception {
        TeacherDTO invalidTeacherDTO = new TeacherDTO();
        invalidTeacherDTO.setId(1L);
        invalidTeacherDTO.setName("J");
        invalidTeacherDTO.setEmail("invalid-email");
        invalidTeacherDTO.setSchoolId(null);

        mockMvc.perform(post("/teacher/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidTeacherDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 100 characters"))
                .andExpect(jsonPath("$.email").value("Email should be valid"))
                .andExpect(jsonPath("$.schoolId").value("School ID cannot be null"));
    }

    @Test
    void testAddQualificationToTeacher() throws Exception {
        when(teacherService.addQualificationToTeacher(eq(1L), eq(1L))).thenReturn(teacherDTO);

        mockMvc.perform(post("/teacher/1/qualifications/1"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(teacherService, times(1)).addQualificationToTeacher(eq(1L), eq(1L));
    }

    @Test
    void testRemoveQualificationFromTeacher() throws Exception {
        when(teacherService.deleteQualificationFromTeacher(eq(1L), eq(1L))).thenReturn(teacherDTO);

        mockMvc.perform(delete("/teacher/1/qualifications/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(teacherService, times(1)).deleteQualificationFromTeacher(eq(1L), eq(1L));
    }

    @Test
    void testGetTeacherQualifications() throws Exception {
        TeacherQualificationDto qualificationDTO = new TeacherQualificationDto();
        qualificationDTO.setId(1L);
        qualificationDTO.setQualificationEnum(TeacherQualificationEnum.PERMIT_ART_TEACHING);
        when(teacherService.getQualificationsByTeacherId(1L)).thenReturn(Collections.singletonList(qualificationDTO));

        mockMvc.perform(get("/teacher/1/qualifications"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].qualificationEnum").value("PERMIT_ART_TEACHING"));

        verify(teacherService, times(1)).getQualificationsByTeacherId(1L);
    }

    @Test
    void testGetTeachersFromSchoolId() throws Exception {
        when(teacherService.getTeachersFromSchool(1L)).thenReturn(Collections.singletonList(teacherDTO));

        mockMvc.perform(get("/teacher/school-id/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(teacherService, times(1)).getTeachersFromSchool(1L);
    }
}