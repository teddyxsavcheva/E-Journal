package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.HeadmasterDTO;
import com.nbu.ejournalgroupproject.mappers.HeadmasterMapper;
import com.nbu.ejournalgroupproject.model.Headmaster;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.repository.HeadmasterRepository;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.HeadmasterServiceImpl;
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
public class HeadmasterServiceTests {

    @Mock
    HeadmasterRepository headmasterRepository;

    @Mock
    SchoolRepository schoolRepository;

    @Mock
    HeadmasterMapper headmasterMapper;

    @InjectMocks
    HeadmasterServiceImpl headmasterService;

    Headmaster headmaster;
    HeadmasterDTO headmasterDTO;
    School school;

    @BeforeEach
    void setUp() {
        school = new School();
        school.setId(1L);
        school.setName("Test School");
        school.setAddress("123 Test St");

        headmaster = new Headmaster();
        headmaster.setId(1L);
        headmaster.setName("Test Headmaster");
        headmaster.setEmail("headmaster@test.com");
        headmaster.setSchool(school);

        headmasterDTO = new HeadmasterDTO();
        headmasterDTO.setId(1L);
        headmasterDTO.setName("Test Headmaster");
        headmasterDTO.setEmail("headmaster@test.com");
        headmasterDTO.setSchoolId(1L);
    }

    @Test
    public void headmasterService_getHeadmasters_returnsHeadmasters() {
        when(headmasterRepository.findAll()).thenReturn(List.of(headmaster));
        when(headmasterMapper.mapEntityToDto(any(Headmaster.class))).thenReturn(headmasterDTO);

        List<HeadmasterDTO> result = headmasterService.getHeadmasters();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Headmaster", result.get(0).getName());
        verify(headmasterRepository, times(1)).findAll();
        verify(headmasterMapper, times(1)).mapEntityToDto(any(Headmaster.class));
    }

    @Test
    public void headmasterService_getHeadmaster_returnsHeadmaster() {
        when(headmasterRepository.findById(1L)).thenReturn(Optional.of(headmaster));
        when(headmasterMapper.mapEntityToDto(any(Headmaster.class))).thenReturn(headmasterDTO);

        HeadmasterDTO result = headmasterService.getHeadmaster(1L);

        assertNotNull(result);
        assertEquals("Test Headmaster", result.getName());
        verify(headmasterRepository, times(1)).findById(1L);
        verify(headmasterMapper, times(1)).mapEntityToDto(any(Headmaster.class));
    }

    @Test
    public void headmasterService_getHeadmaster_throwsException() {
        when(headmasterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> headmasterService.getHeadmaster(1L));
        verify(headmasterRepository, times(1)).findById(1L);
    }

    @Test
    public void headmasterService_getHeadmasterBySchoolID_returnsHeadmaster() {
        when(headmasterRepository.findBySchoolId(1L)).thenReturn(headmaster);
        when(headmasterMapper.mapEntityToDto(any(Headmaster.class))).thenReturn(headmasterDTO);

        HeadmasterDTO result = headmasterService.getHeadmasterBySchoolID(1L);

        assertNotNull(result);
        assertEquals("Test Headmaster", result.getName());
        verify(headmasterRepository, times(1)).findBySchoolId(1L);
        verify(headmasterMapper, times(1)).mapEntityToDto(any(Headmaster.class));
    }

    @Test
    public void headmasterService_createHeadmaster_returnsHeadmaster() {
        when(headmasterMapper.mapDtoToEntity(any(HeadmasterDTO.class))).thenReturn(headmaster);
        when(headmasterRepository.save(any(Headmaster.class))).thenReturn(headmaster);
        when(headmasterMapper.mapEntityToDto(any(Headmaster.class))).thenReturn(headmasterDTO);

        HeadmasterDTO result = headmasterService.createHeadmaster(headmasterDTO);

        assertNotNull(result);
        assertEquals("Test Headmaster", result.getName());
        verify(headmasterMapper, times(1)).mapDtoToEntity(any(HeadmasterDTO.class));
        verify(headmasterRepository, times(1)).save(any(Headmaster.class));
        verify(headmasterMapper, times(1)).mapEntityToDto(any(Headmaster.class));
    }

    @Test
    public void headmasterService_updateHeadmaster_returnsHeadmaster() {
        when(headmasterRepository.findById(1L)).thenReturn(Optional.of(headmaster));
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(headmasterRepository.save(any(Headmaster.class))).thenReturn(headmaster);
        when(headmasterMapper.mapEntityToDto(any(Headmaster.class))).thenReturn(headmasterDTO);

        HeadmasterDTO result = headmasterService.updateHeadmaster(1L, headmasterDTO);

        assertNotNull(result);
        assertEquals("Test Headmaster", result.getName());
        verify(headmasterRepository, times(1)).findById(1L);
        verify(schoolRepository, times(1)).findById(1L);
        verify(headmasterRepository, times(1)).save(any(Headmaster.class));
        verify(headmasterMapper, times(1)).mapEntityToDto(any(Headmaster.class));
    }

    @Test
    public void headmasterService_updateHeadmaster_throwsException() {
        when(headmasterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> headmasterService.updateHeadmaster(1L, headmasterDTO));
        verify(headmasterRepository, times(1)).findById(1L);
    }

    @Test
    public void headmasterService_deleteHeadmaster_returnsVoid() {
        when(headmasterRepository.findById(1L)).thenReturn(Optional.of(headmaster));

        headmasterService.deleteHeadmaster(1L);

        verify(headmasterRepository, times(1)).findById(1L);
        verify(headmasterRepository, times(1)).delete(any(Headmaster.class));
    }

    @Test
    public void headmasterService_deleteHeadmaster_throwsException() {
        when(headmasterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> headmasterService.deleteHeadmaster(1L));
        verify(headmasterRepository, times(1)).findById(1L);
    }

    @Test
    public void headmasterService_validateHeadmasterDTO_throwsException_whenSchoolIdIsZero() {
        HeadmasterDTO invalidHeadmasterDTO = new HeadmasterDTO();
        invalidHeadmasterDTO.setSchoolId(0L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> headmasterService.validateHeadmasterDTO(invalidHeadmasterDTO));
        assertEquals("The School ID cannot be zero.", exception.getMessage());
    }
}