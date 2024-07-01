package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.DisciplineTypeDto;
import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.mappers.DisciplineTypeMapper;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import com.nbu.ejournalgroupproject.repository.DisciplineTypeRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.DisciplineTypeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DisciplineTypeServiceTests {

    @Mock
    DisciplineTypeRepository disciplineTypeRepository;

    @Mock
    DisciplineTypeMapper disciplineTypeMapper;

    @InjectMocks
    DisciplineTypeServiceImpl disciplineTypeService;

    DisciplineType disciplineType;
    DisciplineTypeDto disciplineTypeDto;

    @BeforeEach
    void setUp() {
        disciplineType = new DisciplineType();
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.BIOLOGY);

        disciplineTypeDto = new DisciplineTypeDto();
        disciplineTypeDto.setDisciplineType(DisciplineTypeEnum.BIOLOGY);
    }

    @AfterEach
    void tearDown() {
        disciplineTypeRepository.deleteAll();
    }

    @Test
    public void disciplineTypeService_getAllDisciplineTypes_returnsAllDisciplineTypes() {
        when(disciplineTypeRepository.findAll()).thenReturn(List.of(disciplineType));
        when(disciplineTypeMapper.convertToDto(any(DisciplineType.class))).thenReturn(disciplineTypeDto);

        List<DisciplineTypeDto> result = disciplineTypeService.getAllDisciplineTypes();

        assertEquals(1, result.size());
        verify(disciplineTypeRepository, times(1)).findAll();
        verify(disciplineTypeMapper, times(1)).convertToDto(any(DisciplineType.class));
    }

    @Test
    public void disciplineTypeService_getDisciplineTypeById_returnsDisciplineType() {
        when(disciplineTypeRepository.findById(1L)).thenReturn(Optional.of(disciplineType));
        when(disciplineTypeMapper.convertToDto(any(DisciplineType.class))).thenReturn(disciplineTypeDto);

        DisciplineTypeDto result = disciplineTypeService.getDisciplineTypeById(1L);

        assertNotNull(result);
        assertEquals(DisciplineTypeEnum.BIOLOGY, result.getDisciplineType());
        verify(disciplineTypeRepository, times(1)).findById(1L);
        verify(disciplineTypeMapper, times(1)).convertToDto(any(DisciplineType.class));
    }

    @Test
    public void disciplineTypeService_getDisciplineTypeById_throwsException() {
        when(disciplineTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> disciplineTypeService.getDisciplineTypeById(1L));
        verify(disciplineTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void disciplineTypeService_createDisciplineType_returnsDisciplineType() {
        when(disciplineTypeMapper.convertToEntity(any(DisciplineTypeDto.class))).thenReturn(disciplineType);
        when(disciplineTypeRepository.save(any(DisciplineType.class))).thenReturn(disciplineType);
        when(disciplineTypeMapper.convertToDto(any(DisciplineType.class))).thenReturn(disciplineTypeDto);

        DisciplineTypeDto result = disciplineTypeService.createDisciplineType(disciplineTypeDto);

        assertNotNull(result);
        assertEquals(DisciplineTypeEnum.BIOLOGY, result.getDisciplineType());
        verify(disciplineTypeMapper, times(1)).convertToEntity(any(DisciplineTypeDto.class));
        verify(disciplineTypeRepository, times(1)).save(any(DisciplineType.class));
        verify(disciplineTypeMapper, times(1)).convertToDto(any(DisciplineType.class));
    }

    @Test
    public void disciplineTypeService_updateDisciplineType_returnsDisciplineType() {
        DisciplineTypeDto newDisciplineTypeDto = new DisciplineTypeDto();
        newDisciplineTypeDto.setDisciplineType(DisciplineTypeEnum.CHEMISTRY);

        when(disciplineTypeRepository.findById(1L)).thenReturn(Optional.of(disciplineType));
        when(disciplineTypeRepository.save(any(DisciplineType.class))).thenReturn(disciplineType);
        when(disciplineTypeMapper.convertToDto(any(DisciplineType.class))).thenReturn(newDisciplineTypeDto);

        DisciplineTypeDto result = disciplineTypeService.updateDisciplineType(newDisciplineTypeDto, 1L);

        assertNotNull(result);
        assertEquals(DisciplineTypeEnum.CHEMISTRY, result.getDisciplineType());
        verify(disciplineTypeRepository, times(1)).findById(1L);
        verify(disciplineTypeRepository, times(1)).save(any(DisciplineType.class));
        verify(disciplineTypeMapper, times(1)).convertToDto(any(DisciplineType.class));
    }

    @Test
    public void disciplineTypeService_updateDisciplineType_throwsException() {
        when(disciplineTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> disciplineTypeService.updateDisciplineType(disciplineTypeDto, 1L));
        verify(disciplineTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void disciplineTypeService_deleteDisciplineType_returnsVoid() {
        when(disciplineTypeRepository.findById(1L)).thenReturn(Optional.of(disciplineType));

        disciplineTypeService.deleteDisciplineType(1L);

        verify(disciplineTypeRepository, times(1)).findById(1L);
        verify(disciplineTypeRepository, times(1)).delete(any(DisciplineType.class));
    }

    @Test
    public void disciplineTypeService_deleteDisciplineType_throwsException() {
        when(disciplineTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> disciplineTypeService.deleteDisciplineType(1L));
        verify(disciplineTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void disciplineTypeService_validateDisciplineTypeDTO_throwsException_whenDisciplineTypeIsNull() {
        DisciplineTypeDto invalidDto = new DisciplineTypeDto();

        assertThrows(IllegalArgumentException.class, () -> disciplineTypeService.validateDisciplineTypeDTO(invalidDto));
    }

}
