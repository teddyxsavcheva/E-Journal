package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.AbsenceDTO;
import com.nbu.ejournalgroupproject.enums.AbsenceStatusEnum;
import com.nbu.ejournalgroupproject.enums.AbsenceTypeEnum;
import com.nbu.ejournalgroupproject.mappers.AbsenceMapper;
import com.nbu.ejournalgroupproject.model.*;
import com.nbu.ejournalgroupproject.repository.AbsenceRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.AbsenceServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AbsenceServiceTests {

    @Mock
    private AbsenceRepository absenceRepository;

    @Mock
    private AbsenceMapper absenceMapper;

    @InjectMocks
    private AbsenceServiceImpl absenceService;

    private Absence absence;
    private AbsenceDTO absenceDTO;
    private AbsenceStatus absenceStatus;
    private AbsenceType absenceType;

    @BeforeEach
    void setUp() {
        absenceStatus = new AbsenceStatus();
        absenceStatus.setId(1L);
        absenceStatus.setAbsenceStatusEnum(AbsenceStatusEnum.EXCUSED);

        absenceType = new AbsenceType();
        absenceType.setId(1L);
        absenceType.setAbsenceTypeEnum(AbsenceTypeEnum.SICK_LEAVE);

        absence = new Absence();
        absence.setId(1L);
        absence.setDateOfIssue(LocalDate.now());
        absence.setAbsenceStatus(absenceStatus);
        absence.setAbsenceType(absenceType);
        absence.setDiscipline(new Discipline());
        absence.setStudent(new Student());

        absenceDTO = new AbsenceDTO();
        absenceDTO.setId(1L);
        absenceDTO.setDateOfIssue(LocalDate.now());
        absenceDTO.setAbsenceStatusId(1L);
        absenceDTO.setAbsenceTypeId(1L);
        absenceDTO.setDisciplineId(1L);
        absenceDTO.setStudentId(1L);
    }

    @Test
    void testGetAllAbsences() {
        when(absenceRepository.findAll()).thenReturn(List.of(absence));
        when(absenceMapper.toDTO(any(Absence.class))).thenReturn(absenceDTO);

        List<AbsenceDTO> result = absenceService.getAllAbsences();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getAbsenceStatusId());
        verify(absenceRepository, times(1)).findAll();
        verify(absenceMapper, times(1)).toDTO(any(Absence.class));
    }

    @Test
    void testGetAbsenceById() {
        when(absenceRepository.findById(1L)).thenReturn(Optional.of(absence));
        when(absenceMapper.toDTO(any(Absence.class))).thenReturn(absenceDTO);

        AbsenceDTO result = absenceService.getAbsenceById(1L);

        assertNotNull(result);
        assertEquals(1, result.getAbsenceStatusId());
        verify(absenceRepository, times(1)).findById(1L);
        verify(absenceMapper, times(1)).toDTO(any(Absence.class));
    }

    @Test
    void testGetAbsenceByIdThrowsException() {
        when(absenceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> absenceService.getAbsenceById(1L));
        verify(absenceRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateAbsence() {
        when(absenceMapper.toEntity(any(AbsenceDTO.class))).thenReturn(absence);
        when(absenceRepository.save(any(Absence.class))).thenReturn(absence);
        when(absenceMapper.toDTO(any(Absence.class))).thenReturn(absenceDTO);

        AbsenceDTO result = absenceService.createAbsence(absenceDTO);

        assertNotNull(result);
        assertEquals(1, result.getAbsenceStatusId());
        verify(absenceMapper, times(1)).toEntity(any(AbsenceDTO.class));
        verify(absenceRepository, times(1)).save(any(Absence.class));
        verify(absenceMapper, times(1)).toDTO(any(Absence.class));
    }

    @Test
    void testUpdateAbsence() {
        when(absenceRepository.findById(1L)).thenReturn(Optional.of(absence));
        when(absenceMapper.toEntity(any(AbsenceDTO.class))).thenReturn(absence);
        when(absenceRepository.save(any(Absence.class))).thenReturn(absence);
        when(absenceMapper.toDTO(any(Absence.class))).thenReturn(absenceDTO);

        AbsenceDTO result = absenceService.updateAbsence(1L, absenceDTO);

        assertNotNull(result);
        assertEquals(1, result.getAbsenceStatusId());
        verify(absenceRepository, times(1)).findById(1L);
        verify(absenceMapper, times(1)).toEntity(any(AbsenceDTO.class));
        verify(absenceRepository, times(1)).save(any(Absence.class));
        verify(absenceMapper, times(1)).toDTO(any(Absence.class));
    }

    @Test
    void testDeleteAbsence() {
        when(absenceRepository.existsById(1L)).thenReturn(true);

        absenceService.deleteAbsence(1L);

        verify(absenceRepository, times(1)).existsById(1L);
        verify(absenceRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAbsenceThrowsException() {
        when(absenceRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> absenceService.deleteAbsence(1L));
        verify(absenceRepository, times(1)).existsById(1L);
    }

    @Test
    void testGetAbsencesByStudentAndDiscipline() {
        when(absenceRepository.excusedAbsencesCountByStudentAndDiscipline(1L, 1L)).thenReturn(3L);
        when(absenceRepository.notExcusedAbsencesCountByStudentAndDiscipline(1L, 1L)).thenReturn(2L);

        List<Long> result = absenceService.getAbsencesByStudentAndDiscipline(1L, 1L);

        assertEquals(2, result.size());
        assertEquals(3L, result.get(0));
        assertEquals(2L, result.get(1));

        verify(absenceRepository, times(1)).excusedAbsencesCountByStudentAndDiscipline(1L, 1L);
        verify(absenceRepository, times(1)).notExcusedAbsencesCountByStudentAndDiscipline(1L, 1L);
    }

    @Test
    void testGetAbsenceObjectsByStudentAndDiscipline() {
        when(absenceRepository.getAllByDisciplineIdAndStudentId(1L, 1L)).thenReturn(List.of(absence));
        when(absenceMapper.toDTO(any(Absence.class))).thenReturn(absenceDTO);

        List<AbsenceDTO> result = absenceService.getAbsenceObjectsByStudentAndDiscipline(1L, 1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        verify(absenceRepository, times(1)).getAllByDisciplineIdAndStudentId(1L, 1L);
        verify(absenceMapper, times(1)).toDTO(any(Absence.class));
    }
}