package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.GradeTypeDTO;
import com.nbu.ejournalgroupproject.enums.GradeTypeEnum;
import com.nbu.ejournalgroupproject.mappers.GradeTypeMapper;
import com.nbu.ejournalgroupproject.model.GradeType;
import com.nbu.ejournalgroupproject.repository.GradeTypeRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.GradeTypeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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
public class GradeTypeServiceTests {

    @Mock
    private GradeTypeRepository gradeTypeRepository;

    @Mock
    private GradeTypeMapper gradeTypeMapper;

    @InjectMocks
    private GradeTypeServiceImpl gradeTypeService;

    private GradeType gradeType;
    private GradeTypeDTO gradeTypeDTO;

    @BeforeEach
    void setUp() {
        gradeType = new GradeType();
        gradeType.setId(1L);
        gradeType.setGradeTypeEnum(GradeTypeEnum.A);

        gradeTypeDTO = new GradeTypeDTO();
        gradeTypeDTO.setId(1L);
        gradeTypeDTO.setGradeTypeEnum(GradeTypeEnum.A);
    }

    @Test
    public void gradeTypeService_getAllGradeTypes_returnsAllGradeTypes() {
        when(gradeTypeRepository.findAll()).thenReturn(List.of(gradeType));
        when(gradeTypeMapper.toDTO(any(GradeType.class))).thenReturn(gradeTypeDTO);

        List<GradeTypeDTO> result = gradeTypeService.getAllGradeTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(GradeTypeEnum.A, result.get(0).getGradeTypeEnum());
        verify(gradeTypeRepository, times(1)).findAll();
        verify(gradeTypeMapper, times(1)).toDTO(any(GradeType.class));
    }

    @Test
    public void gradeTypeService_getGradeTypeById_returnsGradeType() {
        when(gradeTypeRepository.findById(1L)).thenReturn(Optional.of(gradeType));
        when(gradeTypeMapper.toDTO(any(GradeType.class))).thenReturn(gradeTypeDTO);

        GradeTypeDTO result = gradeTypeService.getGradeTypeById(1L);

        assertNotNull(result);
        assertEquals(GradeTypeEnum.A, result.getGradeTypeEnum());
        verify(gradeTypeRepository, times(1)).findById(1L);
        verify(gradeTypeMapper, times(1)).toDTO(any(GradeType.class));
    }

    @Test
    public void gradeTypeService_getGradeTypeById_throwsException() {
        when(gradeTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gradeTypeService.getGradeTypeById(1L));
        verify(gradeTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void gradeTypeService_createGradeType_returnsGradeType() {
        when(gradeTypeMapper.toEntity(any(GradeTypeDTO.class))).thenReturn(gradeType);
        when(gradeTypeRepository.save(any(GradeType.class))).thenReturn(gradeType);
        when(gradeTypeMapper.toDTO(any(GradeType.class))).thenReturn(gradeTypeDTO);

        GradeTypeDTO result = gradeTypeService.createGradeType(gradeTypeDTO);

        assertNotNull(result);
        assertEquals(GradeTypeEnum.A, result.getGradeTypeEnum());
        verify(gradeTypeMapper, times(1)).toEntity(any(GradeTypeDTO.class));
        verify(gradeTypeRepository, times(1)).save(any(GradeType.class));
        verify(gradeTypeMapper, times(1)).toDTO(any(GradeType.class));
    }

    @Test
    public void gradeTypeService_updateGradeType_returnsGradeType() {
        when(gradeTypeRepository.findById(1L)).thenReturn(Optional.of(gradeType));
        when(gradeTypeMapper.toEntity(any(GradeTypeDTO.class))).thenReturn(gradeType);
        when(gradeTypeRepository.save(any(GradeType.class))).thenReturn(gradeType);
        when(gradeTypeMapper.toDTO(any(GradeType.class))).thenReturn(gradeTypeDTO);

        GradeTypeDTO result = gradeTypeService.updateGradeType(1L, gradeTypeDTO);

        assertNotNull(result);
        assertEquals(GradeTypeEnum.A, result.getGradeTypeEnum());
        verify(gradeTypeRepository, times(1)).findById(1L);
        verify(gradeTypeMapper, times(1)).toEntity(any(GradeTypeDTO.class));
        verify(gradeTypeRepository, times(1)).save(any(GradeType.class));
        verify(gradeTypeMapper, times(1)).toDTO(any(GradeType.class));
    }

    @Test
    public void gradeTypeService_updateGradeType_throwsException() {
        when(gradeTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gradeTypeService.updateGradeType(1L, gradeTypeDTO));
        verify(gradeTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void gradeTypeService_deleteGradeType_deletesGradeType() {
        doNothing().when(gradeTypeRepository).deleteById(1L);

        gradeTypeService.deleteGradeType(1L);

        verify(gradeTypeRepository, times(1)).deleteById(1L);
    }
}