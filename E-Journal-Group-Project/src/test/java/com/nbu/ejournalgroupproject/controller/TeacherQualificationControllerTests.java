package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import com.nbu.ejournalgroupproject.service.TeacherQualificationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = TeacherQualificationController.class)
// So that we don't add the spring security
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TeacherQualificationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeacherQualificationService teacherQualificationService;

    @Test
    public void testGetAllTeacherQualifications() throws Exception {
        TeacherQualificationDto qualification1 = new TeacherQualificationDto(1L, TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, null, null);
        TeacherQualificationDto qualification2 = new TeacherQualificationDto(2L, TeacherQualificationEnum.PERMIT_CHEMISTRY_TEACHING, null, null);
        List<TeacherQualificationDto> qualifications = Arrays.asList(qualification1, qualification2);

        when(teacherQualificationService.getAllTeacherQualifications()).thenReturn(qualifications);

        mockMvc.perform(get("/teacher-qualifications/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(qualification1.getId()))
                .andExpect(jsonPath("$[1].id").value(qualification2.getId()));

        verify(teacherQualificationService, times(1)).getAllTeacherQualifications();
    }

    @Test
    public void testGetTeacherQualificationById() throws Exception {
        TeacherQualificationDto qualification = new TeacherQualificationDto(1L, TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, null, null);

        when(teacherQualificationService.getTeacherQualificationById(1L)).thenReturn(qualification);

        mockMvc.perform(get("/teacher-qualifications/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(qualification.getId()));

        verify(teacherQualificationService, times(1)).getTeacherQualificationById(1L);
    }

    @Test
    public void testCreateTeacherQualification() throws Exception {
        TeacherQualificationDto qualification = new TeacherQualificationDto(1L, TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, null, null);

        when(teacherQualificationService.createTeacherQualification(any(TeacherQualificationDto.class))).thenReturn(qualification);

        mockMvc.perform(post("/teacher-qualifications/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(qualification)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(qualification.getId()));

        verify(teacherQualificationService, times(1)).createTeacherQualification(any(TeacherQualificationDto.class));
    }

    @Test
    public void testUpdateTeacherQualification() throws Exception {
        TeacherQualificationDto qualification = new TeacherQualificationDto(1L, TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, null, null);

        when(teacherQualificationService.updateTeacherQualification(any(TeacherQualificationDto.class), eq(1L))).thenReturn(qualification);

        mockMvc.perform(put("/teacher-qualifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(qualification)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(qualification.getId()));

        verify(teacherQualificationService, times(1)).updateTeacherQualification(any(TeacherQualificationDto.class), eq(1L));
    }

    @Test
    public void testDeleteTeacherQualification() throws Exception {
        doNothing().when(teacherQualificationService).deleteTeacherQualification(1L);

        mockMvc.perform(delete("/teacher-qualifications/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(teacherQualificationService, times(1)).deleteTeacherQualification(1L);
    }

    @Test
    public void testAddTeacherToQualification() throws Exception {
        TeacherQualificationDto qualification = new TeacherQualificationDto(1L, TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, null, null);

        when(teacherQualificationService.addTeacherToQualification(1L, 1L)).thenReturn(qualification);

        mockMvc.perform(post("/teacher-qualifications/1/teachers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(qualification.getId()));

        verify(teacherQualificationService, times(1)).addTeacherToQualification(1L, 1L);
    }

    @Test
    public void testRemoveTeacherFromQualification() throws Exception {
        TeacherQualificationDto qualification = new TeacherQualificationDto(1L, TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, null, null);

        when(teacherQualificationService.removeTeacherFromQualification(1L, 1L)).thenReturn(qualification);

        mockMvc.perform(delete("/teacher-qualifications/1/teachers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(qualification.getId()));

        verify(teacherQualificationService, times(1)).removeTeacherFromQualification(1L, 1L);
    }

    @Test
    public void testAddDisciplineToQualification() throws Exception {
        TeacherQualificationDto qualification = new TeacherQualificationDto(1L, TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, null, null);

        when(teacherQualificationService.addDisciplineToQualification(1L, 1L)).thenReturn(qualification);

        mockMvc.perform(post("/teacher-qualifications/1/disciplines/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(qualification.getId()));

        verify(teacherQualificationService, times(1)).addDisciplineToQualification(1L, 1L);
    }

    @Test
    public void testRemoveDisciplineFromQualification() throws Exception {
        TeacherQualificationDto qualification = new TeacherQualificationDto(1L, TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, null, null);

        when(teacherQualificationService.removeDisciplineFromQualification(1L, 1L)).thenReturn(qualification);

        mockMvc.perform(delete("/teacher-qualifications/1/disciplines/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(qualification.getId()));

        verify(teacherQualificationService, times(1)).removeDisciplineFromQualification(1L, 1L);
    }

}
