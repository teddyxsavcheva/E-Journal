package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.StudentCurriculumHasTeacherAndDisciplineDto;
import com.nbu.ejournalgroupproject.service.StudentCurriculumHasTeacherAndDisciplineService;
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

@WebMvcTest(controllers = StudentCurriculumHasTeacherAndDisciplineController.class)
// So that we don't add the spring security
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StudentCurriculumHasTeacherAndDisciplineControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentCurriculumHasTeacherAndDisciplineService curriculumHasTeacherAndDisciplineService;

    @Test
    void getAllCurriculumsTeachersDisciplines_ReturnsListOfDtos() throws Exception {
        List<StudentCurriculumHasTeacherAndDisciplineDto> dtos = Arrays.asList(
                new StudentCurriculumHasTeacherAndDisciplineDto(1L, 1L, 1L, 1L),
                new StudentCurriculumHasTeacherAndDisciplineDto(2L, 2L, 2L, 2L)
        );

        when(curriculumHasTeacherAndDisciplineService.getAllCurriculumHasTeacherAndDiscipline()).thenReturn(dtos);

        mockMvc.perform(get("/curriculums-teachers-disciplines/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(dtos.size()))
                .andExpect(jsonPath("$[0].id").value(dtos.get(0).getId()))
                .andExpect(jsonPath("$[0].disciplineId").value(dtos.get(0).getDisciplineId()))
                .andExpect(jsonPath("$[0].teacherId").value(dtos.get(0).getTeacherId()))
                .andExpect(jsonPath("$[0].curriculumId").value(dtos.get(0).getCurriculumId()))
                .andExpect(jsonPath("$[1].id").value(dtos.get(1).getId()))
                .andExpect(jsonPath("$[1].disciplineId").value(dtos.get(1).getDisciplineId()))
                .andExpect(jsonPath("$[1].teacherId").value(dtos.get(1).getTeacherId()))
                .andExpect(jsonPath("$[1].curriculumId").value(dtos.get(1).getCurriculumId()));
    }

    @Test
    void getCurriculumTeacherDisciplineById_ReturnsDto() throws Exception {
        Long id = 1L;
        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto(id, 1L, 1L, 1L);

        when(curriculumHasTeacherAndDisciplineService.getCurriculumHasTeacherAndDisciplineById(id)).thenReturn(dto);

        mockMvc.perform(get("/curriculums-teachers-disciplines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.disciplineId").value(dto.getDisciplineId()))
                .andExpect(jsonPath("$.teacherId").value(dto.getTeacherId()))
                .andExpect(jsonPath("$.curriculumId").value(dto.getCurriculumId()));
    }

    @Test
    void createCurriculumTeacherDiscipline_ReturnsCreatedDto() throws Exception {
        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto(null, 1L, 1L, 1L);
        StudentCurriculumHasTeacherAndDisciplineDto createdDto = new StudentCurriculumHasTeacherAndDisciplineDto(1L, 1L, 1L, 1L);

        when(curriculumHasTeacherAndDisciplineService.createCurriculumHasTeacherAndDiscipline(any(StudentCurriculumHasTeacherAndDisciplineDto.class))).thenReturn(createdDto);

        mockMvc.perform(post("/curriculums-teachers-disciplines/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdDto.getId()))
                .andExpect(jsonPath("$.disciplineId").value(createdDto.getDisciplineId()))
                .andExpect(jsonPath("$.teacherId").value(createdDto.getTeacherId()))
                .andExpect(jsonPath("$.curriculumId").value(createdDto.getCurriculumId()));
    }

    @Test
    void updateCurriculumTeacherDiscipline_ReturnsUpdatedDto() throws Exception {
        Long id = 1L;
        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto(id, 1L, 1L, 1L);
        StudentCurriculumHasTeacherAndDisciplineDto updatedDto = new StudentCurriculumHasTeacherAndDisciplineDto(id, 2L, 2L, 2L);

        when(curriculumHasTeacherAndDisciplineService.updateCurriculumHasTeacherAndDiscipline(any(StudentCurriculumHasTeacherAndDisciplineDto.class), eq(id))).thenReturn(updatedDto);

        mockMvc.perform(put("/curriculums-teachers-disciplines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedDto.getId()))
                .andExpect(jsonPath("$.disciplineId").value(updatedDto.getDisciplineId()))
                .andExpect(jsonPath("$.teacherId").value(updatedDto.getTeacherId()))
                .andExpect(jsonPath("$.curriculumId").value(updatedDto.getCurriculumId()));
    }

    @Test
    void deleteCurriculumTeacherDisciplineById_ReturnsHttpStatusOk() throws Exception {
        Long id = 1L;
        doNothing().when(curriculumHasTeacherAndDisciplineService).deleteCurriculumHasTeacherAndDiscipline(id);

        mockMvc.perform(delete("/curriculums-teachers-disciplines/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCurriculumTeachersDisciplinesByStudCurr_ReturnsListOfDtos() throws Exception {
        Long curriculumId = 1L;
        List<StudentCurriculumHasTeacherAndDisciplineDto> dtos = Arrays.asList(
                new StudentCurriculumHasTeacherAndDisciplineDto(1L, 1L, 1L, curriculumId),
                new StudentCurriculumHasTeacherAndDisciplineDto(2L, 2L, 2L, curriculumId)
        );

        when(curriculumHasTeacherAndDisciplineService.getAllCurriculumTeacherDisciplineByStudCurr(curriculumId)).thenReturn(dtos);

        mockMvc.perform(get("/curriculums-teachers-disciplines/student-curriculum/{id}", curriculumId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(dtos.size()))
                .andExpect(jsonPath("$[0].id").value(dtos.get(0).getId()))
                .andExpect(jsonPath("$[0].disciplineId").value(dtos.get(0).getDisciplineId()))
                .andExpect(jsonPath("$[0].teacherId").value(dtos.get(0).getTeacherId()))
                .andExpect(jsonPath("$[0].curriculumId").value(dtos.get(0).getCurriculumId()))
                .andExpect(jsonPath("$[1].id").value(dtos.get(1).getId()))
                .andExpect(jsonPath("$[1].disciplineId").value(dtos.get(1).getDisciplineId()))
                .andExpect(jsonPath("$[1].teacherId").value(dtos.get(1).getTeacherId()))
                .andExpect(jsonPath("$[1].curriculumId").value(dtos.get(1).getCurriculumId()));
    }
}
