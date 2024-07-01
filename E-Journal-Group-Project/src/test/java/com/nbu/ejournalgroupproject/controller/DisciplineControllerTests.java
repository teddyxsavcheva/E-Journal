package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.DisciplineDto;
import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import com.nbu.ejournalgroupproject.service.DisciplineService;
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

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DisciplineController.class)
// So that we don't add the spring security
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DisciplineControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisciplineService disciplineService;

    @Autowired
    private ObjectMapper objectMapper;

    private DisciplineType disciplineType;

    @BeforeEach
    public void startUp() throws Exception {
        disciplineType = new DisciplineType();
        disciplineType.setId(1L);
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.BIOLOGY);
    }

    @Test
    public void disciplineController_getAllDisciplines_returnsDtos() throws Exception {
        DisciplineDto discipline1 = new DisciplineDto();
        discipline1.setId(1L);
        discipline1.setName("Math");

        DisciplineDto discipline2 = new DisciplineDto();
        discipline2.setId(2L);
        discipline2.setName("Science");

        List<DisciplineDto> disciplineList = Arrays.asList(discipline1, discipline2);

        when(disciplineService.getAllDisciplines()).thenReturn(disciplineList);

        mockMvc.perform(get("/disciplines/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(disciplineList.size()))
                .andExpect(jsonPath("$[0].id").value(discipline1.getId()))
                .andExpect(jsonPath("$[0].name").value(discipline1.getName()))
                .andExpect(jsonPath("$[1].id").value(discipline2.getId()))
                .andExpect(jsonPath("$[1].name").value(discipline2.getName()));

        verify(disciplineService, times(1)).getAllDisciplines();
    }

    @Test
    public void disciplineController_getDisciplineById_returnsDto() throws Exception {
        Long id = 1L;
        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setId(id);
        disciplineDto.setName("Math");

        when(disciplineService.getDisciplineById(id)).thenReturn(disciplineDto);

        mockMvc.perform(get("/disciplines/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(disciplineDto.getId()))
                .andExpect(jsonPath("$.name").value(disciplineDto.getName()));

        verify(disciplineService, times(1)).getDisciplineById(id);
    }

    @Test
    public void disciplineController_createDiscipline_returnsDto() throws Exception {

        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setName("Biology");
        disciplineDto.setDisciplineTypeId(disciplineType.getId());

        DisciplineDto createdDisciplineDto = new DisciplineDto();
        createdDisciplineDto.setId(1L);
        createdDisciplineDto.setName("Biology");
        createdDisciplineDto.setDisciplineTypeId(disciplineType.getId());

        when(disciplineService.createDiscipline(any(DisciplineDto.class))).thenReturn(createdDisciplineDto);

        mockMvc.perform(post("/disciplines/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(disciplineDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(createdDisciplineDto.getId()))
                .andExpect(jsonPath("$.name").value(createdDisciplineDto.getName()))
                .andReturn();

        verify(disciplineService, times(1)).createDiscipline(any(DisciplineDto.class));
    }

    @Test
    public void disciplineController_updateDiscipline_returnsDto() throws Exception {
        DisciplineDto initialDisciplineDto = new DisciplineDto();
        initialDisciplineDto.setId(1L);
        initialDisciplineDto.setName("Biology");
        initialDisciplineDto.setDisciplineTypeId(disciplineType.getId());

        DisciplineDto updatedDisciplineDto = new DisciplineDto();
        updatedDisciplineDto.setId(1L);
        updatedDisciplineDto.setName("New Biology"); // Updated name
        updatedDisciplineDto.setDisciplineTypeId(disciplineType.getId()); // DisciplineTypeId remains the same

        when(disciplineService.updateDiscipline(any(DisciplineDto.class), anyLong())).thenReturn(updatedDisciplineDto);

        // Perform the PUT request to update the discipline
        mockMvc.perform(MockMvcRequestBuilders.put("/disciplines/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initialDisciplineDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Biology")) // Verify updated name
                .andExpect(MockMvcResultMatchers.jsonPath("$.disciplineTypeId").value(1L)); // Verify disciplineTypeId

        // Verify that the service method was called with the correct arguments
        verify(disciplineService, times(1)).updateDiscipline(any(DisciplineDto.class), eq(1L));
    }

    @Test
    public void disciplineController_deleteDiscipline_returnsOkStatus() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/disciplines/{id}", id))
                .andExpect(status().isOk());

        verify(disciplineService, times(1)).deleteDiscipline(id);
    }

    @Test
    public void disciplineController_getQualificationsByDisciplineId_returnsTeacherQualificationDtos() throws Exception {
        TeacherQualificationDto teacherQualificationDto = new TeacherQualificationDto();
        teacherQualificationDto.setId(1L);
        teacherQualificationDto.setQualificationEnum(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING);

        when(disciplineService.getQualificationsByDisciplineId(anyLong())).thenReturn(Collections.singletonList(teacherQualificationDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/disciplines/{id}/qualifications", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].qualificationEnum").value("PERMIT_BIOLOGY_TEACHING"));
    }

    @Test
    public void disciplineController_addQualificationToDiscipline_returnsDto() throws Exception {
        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setId(1L);
        disciplineDto.setName("Mathematics");
        disciplineDto.setDisciplineTypeId(1L);

        when(disciplineService.addQualificationToDiscipline(anyLong(), anyLong())).thenReturn(disciplineDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/disciplines/{disciplineId}/qualifications/{qualificationId}", 1L, 1L))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mathematics"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.disciplineTypeId").value(1L));
    }

    @Test
    public void disciplineController_removeQualificationFromDiscipline_returnsDto() throws Exception {
        DisciplineDto disciplineDto = new DisciplineDto();
        disciplineDto.setId(1L);
        disciplineDto.setName("Mathematics");
        disciplineDto.setDisciplineTypeId(1L);

        when(disciplineService.removeQualificationFromDiscipline(anyLong(), anyLong())).thenReturn(disciplineDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/disciplines/{disciplineId}/qualifications/{qualificationId}", 1L, 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mathematics"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.disciplineTypeId").value(1L));
    }

}
