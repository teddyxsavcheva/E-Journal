package com.nbu.ejournalgroupproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbu.ejournalgroupproject.dto.DisciplineTypeDto;
import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.service.DisciplineTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DisciplineTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DisciplineTypeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DisciplineTypeService disciplineTypeService;

    @Test
    void disciplineTypeController_getAllDisciplineType_ReturnsListOfDisciplineTypes() throws Exception {
        List<DisciplineTypeDto> disciplineTypeDtos = Arrays.asList(
                new DisciplineTypeDto(1L, DisciplineTypeEnum.MATH),
                new DisciplineTypeDto(2L, DisciplineTypeEnum.SCIENCE)
        );

        when(disciplineTypeService.getAllDisciplineTypes()).thenReturn(disciplineTypeDtos);

        mockMvc.perform(get("/discipline-types/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(disciplineTypeDtos.size()))
                .andExpect(jsonPath("$[0].id").value(disciplineTypeDtos.get(0).getId()))
                .andExpect(jsonPath("$[0].disciplineType").value("MATH"))
                .andExpect(jsonPath("$[1].id").value(disciplineTypeDtos.get(1).getId()))
                .andExpect(jsonPath("$[1].disciplineType").value("SCIENCE"));
    }

    @Test
    void disciplineTypeController_getDisciplineTypeById_ReturnsDisciplineTypeDto() throws Exception {
        DisciplineTypeDto disciplineTypeDto = new DisciplineTypeDto(1L, DisciplineTypeEnum.MATH);

        when(disciplineTypeService.getDisciplineTypeById(1L)).thenReturn(disciplineTypeDto);

        mockMvc.perform(get("/discipline-types/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(disciplineTypeDto.getId()))
                .andExpect(jsonPath("$.disciplineType").value("MATH"));
    }

    @Test
    void disciplineTypeController_createDisciplineType_ReturnsCreatedDisciplineTypeDto() throws Exception {
        DisciplineTypeDto disciplineTypeDto = new DisciplineTypeDto(null, DisciplineTypeEnum.MATH);
        DisciplineTypeDto createdDisciplineTypeDto = new DisciplineTypeDto(1L, DisciplineTypeEnum.MATH);

        when(disciplineTypeService.createDisciplineType(any(DisciplineTypeDto.class))).thenReturn(createdDisciplineTypeDto);

        mockMvc.perform(post("/discipline-types/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplineTypeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdDisciplineTypeDto.getId()))
                .andExpect(jsonPath("$.disciplineType").value("MATH"));
    }

    @Test
    void disciplineTypeController_updateDisciplineType_ReturnsUpdatedDisciplineTypeDto() throws Exception {
        DisciplineTypeDto disciplineTypeDto = new DisciplineTypeDto(null, DisciplineTypeEnum.SCIENCE);
        DisciplineTypeDto updatedDisciplineTypeDto = new DisciplineTypeDto(1L, DisciplineTypeEnum.SCIENCE);

        when(disciplineTypeService.updateDisciplineType(any(DisciplineTypeDto.class), eq(1L))).thenReturn(updatedDisciplineTypeDto);

        mockMvc.perform(put("/discipline-types/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplineTypeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedDisciplineTypeDto.getId()))
                .andExpect(jsonPath("$.disciplineType").value("SCIENCE"));
    }

    @Test
    void disciplineTypeController_deleteDisciplineType_ReturnsHttpStatusOk() throws Exception {
        mockMvc.perform(delete("/discipline-types/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(disciplineTypeService, times(1)).deleteDisciplineType(1L);
    }
}
