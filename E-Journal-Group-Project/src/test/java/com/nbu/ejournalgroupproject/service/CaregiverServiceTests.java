package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.CaregiverDTO;
import com.nbu.ejournalgroupproject.mappers.CaregiverMapper;
import com.nbu.ejournalgroupproject.model.Caregiver;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.repository.CaregiverRepository;
import com.nbu.ejournalgroupproject.repository.StudentRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.CaregiverServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CaregiverServiceTests {

    @Mock
    private CaregiverRepository caregiverRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CaregiverMapper caregiverMapper;

    @InjectMocks
    private CaregiverServiceImpl caregiverService;

    private Caregiver caregiver;
    private CaregiverDTO caregiverDTO;
    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("Test Student");

        caregiver = new Caregiver();
        caregiver.setId(1L);
        caregiver.setName("Test Caregiver");
        caregiver.setStudents(new HashSet<>());

        caregiverDTO = new CaregiverDTO();
        caregiverDTO.setId(1L);
        caregiverDTO.setName("Test Caregiver");
    }

    @Test
    public void caregiverService_getAllCaregivers_returnsAllCaregivers() {
        when(caregiverRepository.findAll()).thenReturn(List.of(caregiver));
        when(caregiverMapper.toDTO(any(Caregiver.class))).thenReturn(caregiverDTO);

        List<CaregiverDTO> result = caregiverService.getAllCaregivers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Caregiver", result.get(0).getName());
        verify(caregiverRepository, times(1)).findAll();
        verify(caregiverMapper, times(1)).toDTO(any(Caregiver.class));
    }

    @Test
    public void caregiverService_getCaregiverById_returnsCaregiver() {
        when(caregiverRepository.findById(1L)).thenReturn(Optional.of(caregiver));
        when(caregiverMapper.toDTO(any(Caregiver.class))).thenReturn(caregiverDTO);

        CaregiverDTO result = caregiverService.getCaregiverById(1L);

        assertNotNull(result);
        assertEquals("Test Caregiver", result.getName());
        verify(caregiverRepository, times(1)).findById(1L);
        verify(caregiverMapper, times(1)).toDTO(any(Caregiver.class));
    }

    @Test
    public void caregiverService_getCaregiverById_throwsException() {
        when(caregiverRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> caregiverService.getCaregiverById(1L));
        verify(caregiverRepository, times(1)).findById(1L);
    }

    @Test
    public void caregiverService_createCaregiver_returnsCaregiver() {
        when(caregiverMapper.toEntity(any(CaregiverDTO.class))).thenReturn(caregiver);
        when(caregiverRepository.save(any(Caregiver.class))).thenReturn(caregiver);
        when(caregiverMapper.toDTO(any(Caregiver.class))).thenReturn(caregiverDTO);

        CaregiverDTO result = caregiverService.createCaregiver(caregiverDTO);

        assertNotNull(result);
        assertEquals("Test Caregiver", result.getName());
        verify(caregiverMapper, times(1)).toEntity(any(CaregiverDTO.class));
        verify(caregiverRepository, times(1)).save(any(Caregiver.class));
        verify(caregiverMapper, times(1)).toDTO(any(Caregiver.class));
    }

    @Test
    public void caregiverService_updateCaregiver_returnsCaregiver() {
        when(caregiverRepository.findById(1L)).thenReturn(Optional.of(caregiver));
        when(caregiverMapper.toEntity(any(CaregiverDTO.class))).thenReturn(caregiver);
        when(caregiverRepository.save(any(Caregiver.class))).thenReturn(caregiver);
        when(caregiverMapper.toDTO(any(Caregiver.class))).thenReturn(caregiverDTO);

        CaregiverDTO result = caregiverService.updateCaregiver(1L, caregiverDTO);

        assertNotNull(result);
        assertEquals("Test Caregiver", result.getName());
        verify(caregiverRepository, times(1)).findById(1L);
        verify(caregiverMapper, times(1)).toEntity(any(CaregiverDTO.class));
        verify(caregiverRepository, times(1)).save(any(Caregiver.class));
        verify(caregiverMapper, times(1)).toDTO(any(Caregiver.class));
    }

    @Test
    public void caregiverService_updateCaregiver_throwsException() {
        when(caregiverRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> caregiverService.updateCaregiver(1L, caregiverDTO));
        verify(caregiverRepository, times(1)).findById(1L);
    }

    @Test
    public void caregiverService_deleteCaregiver_deletesCaregiver() {
        doNothing().when(caregiverRepository).deleteById(1L);

        caregiverService.deleteCaregiver(1L);

        verify(caregiverRepository, times(1)).deleteById(1L);
    }

    @Test
    public void caregiverService_addStudentToCaregiver_addsStudent() {
        when(caregiverRepository.findById(1L)).thenReturn(Optional.of(caregiver));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.hasLessThenTwoCaregivers(1L)).thenReturn(true);
        when(caregiverRepository.save(any(Caregiver.class))).thenReturn(caregiver);
        when(caregiverMapper.toDTO(any(Caregiver.class))).thenReturn(caregiverDTO);

        CaregiverDTO result = caregiverService.addStudentToCaregiver(1L, 1L);

        assertNotNull(result);
        assertTrue(caregiver.getStudents().contains(student));
        verify(caregiverRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).hasLessThenTwoCaregivers(1L);
        verify(caregiverRepository, times(1)).save(any(Caregiver.class));
        verify(caregiverMapper, times(1)).toDTO(any(Caregiver.class));
    }

    @Test
    public void caregiverService_addStudentToCaregiver_throwsException() {
        when(caregiverRepository.findById(1L)).thenReturn(Optional.of(caregiver));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.hasLessThenTwoCaregivers(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> caregiverService.addStudentToCaregiver(1L, 1L));
        verify(caregiverRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).hasLessThenTwoCaregivers(1L);
    }

    @Test
    public void caregiverService_removeStudentFromCaregiver_removesStudent() {
        when(caregiverRepository.findById(1L)).thenReturn(Optional.of(caregiver));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(caregiverRepository.save(any(Caregiver.class))).thenReturn(caregiver);
        when(caregiverMapper.toDTO(any(Caregiver.class))).thenReturn(caregiverDTO);

        CaregiverDTO result = caregiverService.removeStudentFromCaregiver(1L, 1L);

        assertNotNull(result);
        assertFalse(caregiver.getStudents().contains(student));
        verify(caregiverRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).findById(1L);
        verify(caregiverRepository, times(1)).save(any(Caregiver.class));
        verify(caregiverMapper, times(1)).toDTO(any(Caregiver.class));
    }

    @Test
    public void caregiverService_getCaregiversFromStudentId_returnsCaregivers() {
        when(caregiverRepository.findCaregiversByStudentId(1L)).thenReturn(List.of(caregiver));
        when(caregiverMapper.toDTO(any(Caregiver.class))).thenReturn(caregiverDTO);

        List<CaregiverDTO> result = caregiverService.getCaregiversFromStudentId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Caregiver", result.get(0).getName());
        verify(caregiverRepository, times(1)).findCaregiversByStudentId(1L);
        verify(caregiverMapper, times(1)).toDTO(any(Caregiver.class));
    }
}