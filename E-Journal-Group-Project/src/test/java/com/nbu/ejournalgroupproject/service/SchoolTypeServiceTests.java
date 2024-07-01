package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.SchoolTypeDTO;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.mappers.SchoolTypeMapper;
import com.nbu.ejournalgroupproject.model.SchoolType;
import com.nbu.ejournalgroupproject.repository.SchoolTypeRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.SchoolTypeServiceImpl;
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
public class SchoolTypeServiceTests {

    @Mock
    SchoolTypeRepository schoolTypeRepository;

    @Mock
    SchoolTypeMapper schoolTypeMapper;

    @InjectMocks
    SchoolTypeServiceImpl schoolTypeService;

    SchoolType schoolType;
    SchoolTypeDTO schoolTypeDTO;

    @BeforeEach
    void setUp() {
        schoolType = new SchoolType();
        schoolType.setSchoolTypeEnum(SchoolTypeEnum.PRIMARY_SCHOOL);

        schoolTypeDTO = new SchoolTypeDTO();
        schoolTypeDTO.setSchoolType(SchoolTypeEnum.PRIMARY_SCHOOL);
    }

    @Test
    public void schoolTypeService_getAllSchoolTypes_returnsAllSchoolTypes() {
        when(schoolTypeRepository.findAll()).thenReturn(List.of(schoolType));
        when(schoolTypeMapper.mapEntityToDto(any(SchoolType.class))).thenReturn(schoolTypeDTO);

        List<SchoolTypeDTO> result = schoolTypeService.getAllSchoolTypes();

        assertEquals(1, result.size());
        verify(schoolTypeRepository, times(1)).findAll();
        verify(schoolTypeMapper, times(1)).mapEntityToDto(any(SchoolType.class));
    }

    @Test
    public void schoolTypeService_getSchoolTypeById_returnsSchoolType() {
        when(schoolTypeRepository.findById(1L)).thenReturn(Optional.of(schoolType));
        when(schoolTypeMapper.mapEntityToDto(any(SchoolType.class))).thenReturn(schoolTypeDTO);

        SchoolTypeDTO result = schoolTypeService.getSchoolTypeById(1L);

        assertNotNull(result);
        assertEquals(SchoolTypeEnum.PRIMARY_SCHOOL, result.getSchoolType());
        verify(schoolTypeRepository, times(1)).findById(1L);
        verify(schoolTypeMapper, times(1)).mapEntityToDto(any(SchoolType.class));
    }

    @Test
    public void schoolTypeService_getSchoolTypeById_throwsException() {
        when(schoolTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolTypeService.getSchoolTypeById(1L));
        verify(schoolTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void schoolTypeService_createSchoolType_returnsSchoolType() {
        when(schoolTypeMapper.mapDtoToEntity(any(SchoolTypeDTO.class))).thenReturn(schoolType);
        when(schoolTypeRepository.save(any(SchoolType.class))).thenReturn(schoolType);
        when(schoolTypeMapper.mapEntityToDto(any(SchoolType.class))).thenReturn(schoolTypeDTO);

        SchoolTypeDTO result = schoolTypeService.createSchoolType(schoolTypeDTO);

        assertNotNull(result);
        assertEquals(SchoolTypeEnum.PRIMARY_SCHOOL, result.getSchoolType());
        verify(schoolTypeMapper, times(1)).mapDtoToEntity(any(SchoolTypeDTO.class));
        verify(schoolTypeRepository, times(1)).save(any(SchoolType.class));
        verify(schoolTypeMapper, times(1)).mapEntityToDto(any(SchoolType.class));
    }

    @Test
    public void schoolTypeService_updateSchoolType_returnsUpdatedSchoolType() {
        SchoolTypeDTO updatedSchoolTypeDTO = new SchoolTypeDTO();
        updatedSchoolTypeDTO.setSchoolType(SchoolTypeEnum.PRIMARY_SCHOOL);

        when(schoolTypeRepository.findById(1L)).thenReturn(Optional.of(schoolType));
        when(schoolTypeRepository.save(any(SchoolType.class))).thenReturn(schoolType);
        when(schoolTypeMapper.mapEntityToDto(any(SchoolType.class))).thenReturn(updatedSchoolTypeDTO);

        SchoolTypeDTO result = schoolTypeService.updateSchoolType(1L, updatedSchoolTypeDTO);

        assertNotNull(result);
        assertEquals(SchoolTypeEnum.PRIMARY_SCHOOL, result.getSchoolType());
        verify(schoolTypeRepository, times(1)).findById(1L);
        verify(schoolTypeRepository, times(1)).save(any(SchoolType.class));
        verify(schoolTypeMapper, times(1)).mapEntityToDto(any(SchoolType.class));
    }

    @Test
    public void schoolTypeService_updateSchoolType_throwsException() {
        when(schoolTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolTypeService.updateSchoolType(1L, schoolTypeDTO));
        verify(schoolTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void schoolTypeService_deleteSchoolType_returnsVoid() {
        when(schoolTypeRepository.findById(1L)).thenReturn(Optional.of(schoolType));

        schoolTypeService.deleteSchoolType(1L);

        verify(schoolTypeRepository, times(1)).findById(1L);
        verify(schoolTypeRepository, times(1)).delete(any(SchoolType.class));
    }

    @Test
    public void schoolTypeService_deleteSchoolType_throwsException() {
        when(schoolTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolTypeService.deleteSchoolType(1L));
        verify(schoolTypeRepository, times(1)).findById(1L);
    }
}