package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.AbsenceTypeDTO;
import com.nbu.ejournalgroupproject.enums.AbsenceTypeEnum;
import com.nbu.ejournalgroupproject.mappers.AbsenceTypeMapper;
import com.nbu.ejournalgroupproject.model.AbsenceType;
import com.nbu.ejournalgroupproject.repository.AbsenceTypeRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.AbsenceTypeServiceImpl;
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
public class AbsenceTypeServiceTests {

    @Mock
    private AbsenceTypeRepository absenceTypeRepository;

    @Mock
    private AbsenceTypeMapper absenceTypeMapper;

    @InjectMocks
    private AbsenceTypeServiceImpl absenceTypeService;

    private AbsenceType absenceType;
    private AbsenceTypeDTO absenceTypeDTO;

    @BeforeEach
    void setUp() {
        absenceType = new AbsenceType();
        absenceType.setId(1L);
        absenceType.setAbsenceTypeEnum(AbsenceTypeEnum.SICK_LEAVE);

        absenceTypeDTO = new AbsenceTypeDTO();
        absenceTypeDTO.setId(1L);
        absenceTypeDTO.setAbsenceTypeEnum(AbsenceTypeEnum.SICK_LEAVE);
    }

    @Test
    public void absenceTypeService_getAllAbsenceTypes_returnsAllAbsenceTypes() {
        when(absenceTypeRepository.findAll()).thenReturn(List.of(absenceType));
        when(absenceTypeMapper.toDTO(any(AbsenceType.class))).thenReturn(absenceTypeDTO);

        List<AbsenceTypeDTO> result = absenceTypeService.getAllAbsenceTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(AbsenceTypeEnum.SICK_LEAVE, result.get(0).getAbsenceTypeEnum());
        verify(absenceTypeRepository, times(1)).findAll();
        verify(absenceTypeMapper, times(1)).toDTO(any(AbsenceType.class));
    }

    @Test
    public void absenceTypeService_getAbsenceTypeById_returnsAbsenceType() {
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        when(absenceTypeMapper.toDTO(any(AbsenceType.class))).thenReturn(absenceTypeDTO);

        AbsenceTypeDTO result = absenceTypeService.getAbsenceTypeById(1L);

        assertNotNull(result);
        assertEquals(AbsenceTypeEnum.SICK_LEAVE, result.getAbsenceTypeEnum());
        verify(absenceTypeRepository, times(1)).findById(1L);
        verify(absenceTypeMapper, times(1)).toDTO(any(AbsenceType.class));
    }

    @Test
    public void absenceTypeService_getAbsenceTypeById_throwsException() {
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> absenceTypeService.getAbsenceTypeById(1L));
        verify(absenceTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void absenceTypeService_createAbsenceType_returnsAbsenceType() {
        when(absenceTypeMapper.toEntity(any(AbsenceTypeDTO.class))).thenReturn(absenceType);
        when(absenceTypeRepository.save(any(AbsenceType.class))).thenReturn(absenceType);
        when(absenceTypeMapper.toDTO(any(AbsenceType.class))).thenReturn(absenceTypeDTO);

        AbsenceTypeDTO result = absenceTypeService.createAbsenceType(absenceTypeDTO);

        assertNotNull(result);
        assertEquals(AbsenceTypeEnum.SICK_LEAVE, result.getAbsenceTypeEnum());
        verify(absenceTypeMapper, times(1)).toEntity(any(AbsenceTypeDTO.class));
        verify(absenceTypeRepository, times(1)).save(any(AbsenceType.class));
        verify(absenceTypeMapper, times(1)).toDTO(any(AbsenceType.class));
    }

    @Test
    public void absenceTypeService_updateAbsenceType_returnsAbsenceType() {
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        when(absenceTypeMapper.toEntity(any(AbsenceTypeDTO.class))).thenReturn(absenceType);
        when(absenceTypeRepository.save(any(AbsenceType.class))).thenReturn(absenceType);
        when(absenceTypeMapper.toDTO(any(AbsenceType.class))).thenReturn(absenceTypeDTO);

        AbsenceTypeDTO result = absenceTypeService.updateAbsenceType(1L, absenceTypeDTO);

        assertNotNull(result);
        assertEquals(AbsenceTypeEnum.SICK_LEAVE, result.getAbsenceTypeEnum());
        verify(absenceTypeRepository, times(1)).findById(1L);
        verify(absenceTypeMapper, times(1)).toEntity(any(AbsenceTypeDTO.class));
        verify(absenceTypeRepository, times(1)).save(any(AbsenceType.class));
        verify(absenceTypeMapper, times(1)).toDTO(any(AbsenceType.class));
    }

    @Test
    public void absenceTypeService_updateAbsenceType_throwsException() {
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> absenceTypeService.updateAbsenceType(1L, absenceTypeDTO));
        verify(absenceTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void absenceTypeService_deleteAbsenceType_deletesAbsenceType() {
        doNothing().when(absenceTypeRepository).deleteById(1L);

        absenceTypeService.deleteAbsenceType(1L);

        verify(absenceTypeRepository, times(1)).deleteById(1L);
    }
}