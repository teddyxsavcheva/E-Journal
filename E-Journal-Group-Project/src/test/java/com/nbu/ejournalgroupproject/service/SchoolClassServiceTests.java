package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;
import com.nbu.ejournalgroupproject.mappers.SchoolClassMapper;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.SchoolClass;
import com.nbu.ejournalgroupproject.repository.SchoolClassRepository;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.SchoolClassServiceImpl;
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
public class SchoolClassServiceTests {

    @Mock
    SchoolClassRepository schoolClassRepository;

    @Mock
    SchoolRepository schoolRepository;

    @Mock
    SchoolClassMapper schoolClassMapper;

    @InjectMocks
    SchoolClassServiceImpl schoolClassService;

    SchoolClass schoolClass;
    SchoolClassDTO schoolClassDTO;
    School school;

    @BeforeEach
    void setUp() {
        school = new School();
        school.setId(1L);
        school.setName("Test School");
        school.setAddress("123 Test Street");

        schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("Test Class");
        schoolClass.setYear(2023);
        schoolClass.setSchool(school);

        schoolClassDTO = new SchoolClassDTO();
        schoolClassDTO.setId(1L);
        schoolClassDTO.setName("Test Class");
        schoolClassDTO.setYear(2023);
        schoolClassDTO.setSchoolId(1L);
    }

    @Test
    public void schoolClassService_getSchoolClasses_returnsSchoolClasses() {
        when(schoolClassRepository.findAll()).thenReturn(List.of(schoolClass));
        when(schoolClassMapper.mapEntityToDto(any(SchoolClass.class))).thenReturn(schoolClassDTO);

        List<SchoolClassDTO> result = schoolClassService.getSchoolClasses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Class", result.get(0).getName());
        verify(schoolClassRepository, times(1)).findAll();
        verify(schoolClassMapper, times(1)).mapEntityToDto(any(SchoolClass.class));
    }

    @Test
    public void schoolClassService_getSchoolClass_returnsSchoolClass() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(schoolClassMapper.mapEntityToDto(any(SchoolClass.class))).thenReturn(schoolClassDTO);

        SchoolClassDTO result = schoolClassService.getSchoolClas(1L);

        assertNotNull(result);
        assertEquals("Test Class", result.getName());
        verify(schoolClassRepository, times(1)).findById(1L);
        verify(schoolClassMapper, times(1)).mapEntityToDto(any(SchoolClass.class));
    }

    @Test
    public void schoolClassService_getSchoolClass_throwsException() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolClassService.getSchoolClas(1L));
        verify(schoolClassRepository, times(1)).findById(1L);
    }

    @Test
    public void schoolClassService_getSchoolClassesBySchoolId_returnsSchoolClasses() {
        when(schoolClassRepository.findAllBySchoolId(1L)).thenReturn(List.of(schoolClass));
        when(schoolClassMapper.mapEntityToDto(any(SchoolClass.class))).thenReturn(schoolClassDTO);

        List<SchoolClassDTO> result = schoolClassService.getSchoolClasBySchoolId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Class", result.get(0).getName());
        verify(schoolClassRepository, times(1)).findAllBySchoolId(1L);
        verify(schoolClassMapper, times(1)).mapEntityToDto(any(SchoolClass.class));
    }

    @Test
    public void schoolClassService_createSchoolClass_returnsSchoolClass() {
        when(schoolClassMapper.mapDtoToEntity(any(SchoolClassDTO.class))).thenReturn(schoolClass);
        when(schoolClassRepository.save(any(SchoolClass.class))).thenReturn(schoolClass);
        when(schoolClassMapper.mapEntityToDto(any(SchoolClass.class))).thenReturn(schoolClassDTO);

        SchoolClassDTO result = schoolClassService.createSchoolClass(schoolClassDTO);

        assertNotNull(result);
        assertEquals("Test Class", result.getName());
        verify(schoolClassMapper, times(1)).mapDtoToEntity(any(SchoolClassDTO.class));
        verify(schoolClassRepository, times(1)).save(any(SchoolClass.class));
        verify(schoolClassMapper, times(1)).mapEntityToDto(any(SchoolClass.class));
    }

    @Test
    public void schoolClassService_updateSchoolClass_returnsSchoolClass() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(schoolClassRepository.save(any(SchoolClass.class))).thenReturn(schoolClass);
        when(schoolClassMapper.mapEntityToDto(any(SchoolClass.class))).thenReturn(schoolClassDTO);

        SchoolClassDTO result = schoolClassService.updateSchoolClass(1L, schoolClassDTO);

        assertNotNull(result);
        assertEquals("Test Class", result.getName());
        verify(schoolClassRepository, times(1)).findById(1L);
        verify(schoolRepository, times(1)).findById(1L);
        verify(schoolClassRepository, times(1)).save(any(SchoolClass.class));
        verify(schoolClassMapper, times(1)).mapEntityToDto(any(SchoolClass.class));
    }

    @Test
    public void schoolClassService_updateSchoolClass_throwsException() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolClassService.updateSchoolClass(1L, schoolClassDTO));
        verify(schoolClassRepository, times(1)).findById(1L);
    }

    @Test
    public void schoolClassService_deleteSchoolClass_returnsVoid() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));

        schoolClassService.deleteSchoolClass(1L);

        verify(schoolClassRepository, times(1)).findById(1L);
        verify(schoolClassRepository, times(1)).delete(any(SchoolClass.class));
    }

    @Test
    public void schoolClassService_deleteSchoolClass_throwsException() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolClassService.deleteSchoolClass(1L));
        verify(schoolClassRepository, times(1)).findById(1L);
    }

    @Test
    public void schoolClassService_validateSchoolClassDTO_throwsException_whenSchoolIdIsZero() {
        SchoolClassDTO invalidSchoolClassDTO = new SchoolClassDTO();
        invalidSchoolClassDTO.setSchoolId(0L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> schoolClassService.validateSchoolClassDTO(invalidSchoolClassDTO));
        assertEquals("The School ID cannot be zero.", exception.getMessage());
    }

    @Test
    public void schoolClassService_validateSchoolClassDTO_throwsException_whenNameIsNull() {
        SchoolClassDTO invalidSchoolClassDTO = new SchoolClassDTO();
        invalidSchoolClassDTO.setSchoolId(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> schoolClassService.validateSchoolClassDTO(invalidSchoolClassDTO));
        assertEquals("The School Class name cannot be null.", exception.getMessage());
    }

    @Test
    public void schoolClassService_validateSchoolClassDTO_throwsException_whenYearIsLessThan2000() {
        SchoolClassDTO invalidSchoolClassDTO = new SchoolClassDTO();
        invalidSchoolClassDTO.setSchoolId(1L);
        invalidSchoolClassDTO.setName("Test Class");
        invalidSchoolClassDTO.setYear(1999);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> schoolClassService.validateSchoolClassDTO(invalidSchoolClassDTO));
        assertEquals("The School Year name cannot be < 2000.", exception.getMessage());
    }

    @Test
    public void schoolClassService_getClassesFromTeacherId_returnsClasses() {
        when(schoolClassRepository.findDistinctClassesByTeacherId(1L)).thenReturn(List.of(schoolClass));
        when(schoolClassMapper.mapEntityToDto(any(SchoolClass.class))).thenReturn(schoolClassDTO);

        List<SchoolClassDTO> result = schoolClassService.getClassesFromTeacherId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Class", result.get(0).getName());
        verify(schoolClassRepository, times(1)).findDistinctClassesByTeacherId(1L);
        verify(schoolClassMapper, times(1)).mapEntityToDto(any(SchoolClass.class));
    }
}