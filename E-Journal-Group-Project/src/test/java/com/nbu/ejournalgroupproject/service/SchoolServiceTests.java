package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.SchoolDTO;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import com.nbu.ejournalgroupproject.mappers.SchoolMapper;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.SchoolType;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.repository.SchoolTypeRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.SchoolServiceImpl;
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
public class SchoolServiceTests {

    @Mock
    SchoolRepository schoolRepository;

    @Mock
    SchoolTypeRepository schoolTypeRepository;

    @Mock
    SchoolMapper schoolMapper;

    @InjectMocks
    SchoolServiceImpl schoolService;

    School school;
    SchoolDTO schoolDTO;
    SchoolType schoolType;

    @BeforeEach
    void setUp() {
        schoolType = new SchoolType();
        schoolType.setSchoolTypeEnum(SchoolTypeEnum.PRIMARY_SCHOOL);

        school = new School();
        school.setName("Test School");
        school.setAddress("123 Test Street");
        school.setSchoolType(schoolType);

        schoolDTO = new SchoolDTO();
        schoolDTO.setName("Test School");
        schoolDTO.setAddress("123 Test Street");
        schoolDTO.setSchoolTypeId(1L);
    }

    @AfterEach
    void tearDown() {
        schoolRepository.deleteAll();
    }

    @Test
    public void schoolService_getSchools_returnsAllSchools() {
        when(schoolRepository.findAll()).thenReturn(List.of(school));
        when(schoolMapper.mapEntityToDto(any(School.class))).thenReturn(schoolDTO);

        List<SchoolDTO> result = schoolService.getSchools();

        assertEquals(1, result.size());
        verify(schoolRepository, times(1)).findAll();
        verify(schoolMapper, times(1)).mapEntityToDto(any(School.class));
    }

    @Test
    public void schoolService_getSchoolById_returnsSchool() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(schoolMapper.mapEntityToDto(any(School.class))).thenReturn(schoolDTO);

        SchoolDTO result = schoolService.getSchool(1L);

        assertNotNull(result);
        assertEquals("Test School", result.getName());
        verify(schoolRepository, times(1)).findById(1L);
        verify(schoolMapper, times(1)).mapEntityToDto(any(School.class));
    }

    @Test
    public void schoolService_getSchoolById_throwsException() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolService.getSchool(1L));
        verify(schoolRepository, times(1)).findById(1L);
    }

    @Test
    public void schoolService_createSchool_returnsSchool() {
        when(schoolMapper.mapDtoToEntity(any(SchoolDTO.class))).thenReturn(school);
        when(schoolRepository.save(any(School.class))).thenReturn(school);
        when(schoolMapper.mapEntityToDto(any(School.class))).thenReturn(schoolDTO);

        SchoolDTO result = schoolService.createSchool(schoolDTO);

        assertNotNull(result);
        assertEquals("Test School", result.getName());
        verify(schoolMapper, times(1)).mapDtoToEntity(any(SchoolDTO.class));
        verify(schoolRepository, times(1)).save(any(School.class));
        verify(schoolMapper, times(1)).mapEntityToDto(any(School.class));
    }

    @Test
    public void schoolService_updateSchool_returnsUpdatedSchool() {
        SchoolDTO updatedSchoolDTO = new SchoolDTO();
        updatedSchoolDTO.setName("Updated School");
        updatedSchoolDTO.setAddress("456 Updated Street");
        updatedSchoolDTO.setSchoolTypeId(1L);

        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(schoolRepository.save(any(School.class))).thenReturn(school);
        when(schoolMapper.mapEntityToDto(any(School.class))).thenReturn(updatedSchoolDTO);
        when(schoolTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(schoolType));

        SchoolDTO result = schoolService.updateSchool(1L, updatedSchoolDTO);

        assertNotNull(result);
        assertEquals("Updated School", result.getName());
        verify(schoolRepository, times(1)).findById(1L);
        verify(schoolRepository, times(1)).save(any(School.class));
        verify(schoolMapper, times(1)).mapEntityToDto(any(School.class));
        verify(schoolTypeRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void schoolService_updateSchool_throwsException() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolService.updateSchool(1L, schoolDTO));
        verify(schoolRepository, times(1)).findById(1L);
    }

    @Test
    public void schoolService_deleteSchool_returnsVoid() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));

        schoolService.deleteSchool(1L);

        verify(schoolRepository, times(1)).findById(1L);
        verify(schoolRepository, times(1)).delete(any(School.class));
    }

    @Test
    public void schoolService_deleteSchool_throwsException() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolService.deleteSchool(1L));
        verify(schoolRepository, times(1)).findById(1L);
    }

    @Test
    public void schoolService_validateSchoolDTO_throwsException_whenNameIsNull() {
        SchoolDTO invalidDto = new SchoolDTO();
        invalidDto.setAddress("Address");
        invalidDto.setSchoolTypeId(1L);

        assertThrows(IllegalArgumentException.class, () -> schoolService.validateSchoolDTO(invalidDto));
    }

    @Test
    public void schoolService_validateSchoolDTO_throwsException_whenAddressIsNull() {
        SchoolDTO invalidDto = new SchoolDTO();
        invalidDto.setName("Name");
        invalidDto.setSchoolTypeId(1L);

        assertThrows(IllegalArgumentException.class, () -> schoolService.validateSchoolDTO(invalidDto));
    }

    @Test
    public void schoolService_validateSchoolDTO_throwsException_whenSchoolTypeIdIsZero() {
        SchoolDTO invalidDto = new SchoolDTO();
        invalidDto.setName("Name");
        invalidDto.setAddress("Address");
        invalidDto.setSchoolTypeId(0L);

        assertThrows(IllegalArgumentException.class, () -> schoolService.validateSchoolDTO(invalidDto));
    }
}