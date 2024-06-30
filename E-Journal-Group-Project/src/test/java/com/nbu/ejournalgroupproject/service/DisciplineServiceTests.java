package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.DisciplineDto;
import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import com.nbu.ejournalgroupproject.mappers.DisciplineMapper;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import com.nbu.ejournalgroupproject.repository.DisciplineRepository;
import com.nbu.ejournalgroupproject.repository.DisciplineTypeRepository;
import com.nbu.ejournalgroupproject.repository.TeacherQualificationRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.DisciplineServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DisciplineServiceTests {

    @Mock
    DisciplineRepository disciplineRepository;

    @Mock
    DisciplineTypeRepository disciplineTypeRepository;

    @Mock
    TeacherQualificationRepository teacherQualificationRepository;

    @Mock
    DisciplineMapper disciplineMapper;

    @InjectMocks
    DisciplineServiceImpl disciplineService;

    Discipline discipline;
    DisciplineDto disciplineDto;
    DisciplineType disciplineType;
    TeacherQualification teacherQualification;

    @BeforeEach
    void setUp() {

        disciplineType = new DisciplineType();
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.BIOLOGY);
        disciplineTypeRepository.save(disciplineType);

        discipline = new Discipline();
        discipline.setName("Biology");
        discipline.setDisciplineType(disciplineType);
        Set<TeacherQualification> qualificationSet = new HashSet<>();
        discipline.setTeacherQualifications(qualificationSet);

        disciplineDto = new DisciplineDto();
        disciplineDto.setName("Biology");
        disciplineDto.setDisciplineTypeId(1L);

        teacherQualification = new TeacherQualification();
        teacherQualification.setQualificationEnum(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING);
        Set<Discipline> disciplineSet = new HashSet<>();
        teacherQualification.setDisciplines(disciplineSet);
    }

    @AfterEach
    void tearDown() {
        disciplineRepository.deleteAll();
        disciplineTypeRepository.deleteAll();
    }

    @Test
    public void disciplineService_getAllDisciplines_returnsAllDisciplines() {
        when(disciplineRepository.findAll()).thenReturn(List.of(discipline));
        when(disciplineMapper.convertToDto(any(Discipline.class))).thenReturn(disciplineDto);

        List<DisciplineDto> result = disciplineService.getAllDisciplines();

        assertEquals(1, result.size());
        verify(disciplineRepository, times(1)).findAll();
        verify(disciplineMapper, times(1)).convertToDto(any(Discipline.class));
    }

    @Test
    public void disciplineService_getDisciplineById_returnsDiscipline() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(disciplineMapper.convertToDto(any(Discipline.class))).thenReturn(disciplineDto);

        DisciplineDto result = disciplineService.getDisciplineById(1L);

        assertNotNull(result);
        assertEquals("Biology", result.getName());
        verify(disciplineRepository, times(1)).findById(1L);
        verify(disciplineMapper, times(1)).convertToDto(any(Discipline.class));
    }

    @Test
    public void disciplineService_getDisciplineById_throwsException() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> disciplineService.getDisciplineById(1L));
        verify(disciplineRepository, times(1)).findById(1L);
    }

    @Test
    public void disciplineService_createDiscipline_returnsDiscipline() {
        when(disciplineMapper.convertToEntity(any(DisciplineDto.class))).thenReturn(discipline);
        when(disciplineRepository.save(any(Discipline.class))).thenReturn(discipline);
        when(disciplineMapper.convertToDto(any(Discipline.class))).thenReturn(disciplineDto);

        DisciplineDto result = disciplineService.createDiscipline(disciplineDto);

        assertNotNull(result);
        assertEquals("Biology", result.getName());
        verify(disciplineMapper, times(1)).convertToEntity(any(DisciplineDto.class));
        verify(disciplineRepository, times(1)).save(any(Discipline.class));
        verify(disciplineMapper, times(1)).convertToDto(any(Discipline.class));
    }

    @Test
    public void disciplineService_updateDiscipline_returnsDiscipline() {
        DisciplineDto newDisciplineDto = new DisciplineDto();
        newDisciplineDto.setName("New Biology name");
        newDisciplineDto.setDisciplineTypeId(1L);

        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(disciplineTypeRepository.findById(1L)).thenReturn(Optional.of(disciplineType));
        when(disciplineRepository.save(any(Discipline.class))).thenReturn(discipline);
        when(disciplineMapper.convertToDto(any(Discipline.class))).thenReturn(newDisciplineDto);

        DisciplineDto result = disciplineService.updateDiscipline(newDisciplineDto, 1L);

        assertNotNull(result);
        assertEquals("New Biology name", result.getName());
        verify(disciplineRepository, times(1)).findById(1L);
        verify(disciplineTypeRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).save(any(Discipline.class));
        verify(disciplineMapper, times(1)).convertToDto(any(Discipline.class));
    }

    @Test
    public void disciplineService_updateDiscipline_throwsException() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> disciplineService.updateDiscipline(disciplineDto, 1L));
        verify(disciplineRepository, times(1)).findById(1L);
    }

    @Test
    public void disciplineService_deleteDiscipline_returnsVoid() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));

        disciplineService.deleteDiscipline(1L);

        verify(disciplineRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).delete(any(Discipline.class));
    }

    @Test
    public void disciplineService_deleteDiscipline_throwsException() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> disciplineService.deleteDiscipline(1L));
        verify(disciplineRepository, times(1)).findById(1L);
    }

    @Test
    public void disciplineService_addQualificationToDiscipline_returnsDiscipline() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));
        when(disciplineRepository.save(any(Discipline.class))).thenReturn(discipline);
        when(disciplineMapper.convertToDto(any(Discipline.class))).thenReturn(disciplineDto);

        DisciplineDto result = disciplineService.addQualificationToDiscipline(1L, 1L);

        assertNotNull(result);
        assertTrue(discipline.getTeacherQualifications().contains(teacherQualification));
        verify(disciplineRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).save(any(Discipline.class));
        verify(disciplineMapper, times(1)).convertToDto(any(Discipline.class));
    }

    @Test
    public void disciplineService_addQualificationToDiscipline_throwsException() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> disciplineService.addQualificationToDiscipline(1L, 1L));
        verify(disciplineRepository, times(1)).findById(1L);
    }

    @Test
    public void disciplineService_removeQualificationFromDiscipline_returnsDiscipline() {
        discipline.getTeacherQualifications().add(teacherQualification);
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));
        when(disciplineRepository.save(any(Discipline.class))).thenReturn(discipline);
        when(disciplineMapper.convertToDto(any(Discipline.class))).thenReturn(disciplineDto);

        DisciplineDto result = disciplineService.removeQualificationFromDiscipline(1L, 1L);

        assertNotNull(result);
        assertFalse(discipline.getTeacherQualifications().contains(teacherQualification));
        verify(disciplineRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).save(any(Discipline.class));
        verify(disciplineMapper, times(1)).convertToDto(any(Discipline.class));
    }

    @Test
    public void disciplineService_removeQualificationFromDiscipline_throwsException() {
        when(disciplineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> disciplineService.removeQualificationFromDiscipline(1L, 1L));
        verify(disciplineRepository, times(1)).findById(1L);
    }

    @Test
    public void disciplineService_validateDisciplineDTO_throwsException_whenNameIsNull() {
        DisciplineDto invalidDto = new DisciplineDto();
        invalidDto.setDisciplineTypeId(1L);

        assertThrows(IllegalArgumentException.class, () -> disciplineService.validateDisciplineDTO(invalidDto));
    }

    @Test
    public void disciplineService_validateDisciplineDTO_throwsException_whenNameIsEmpty() {
        DisciplineDto invalidDto = new DisciplineDto();
        invalidDto.setName("");
        invalidDto.setDisciplineTypeId(1L);

        assertThrows(IllegalArgumentException.class, () -> disciplineService.validateDisciplineDTO(invalidDto));
    }

    @Test
    public void disciplineService_validateDisciplineDTO_throwsException_whenDisciplineTypeIdIsNull() {
        DisciplineDto invalidDto = new DisciplineDto();
        invalidDto.setName("Valid Name");

        assertThrows(IllegalArgumentException.class, () -> disciplineService.validateDisciplineDTO(invalidDto));
    }

    @Test
    public void disciplineService_validateDisciplineDTO_throwsException_whenDisciplineTypeIdIsZero() {
        DisciplineDto invalidDto = new DisciplineDto();
        invalidDto.setName("Valid Name");
        invalidDto.setDisciplineTypeId(0L);

        assertThrows(IllegalArgumentException.class, () -> disciplineService.validateDisciplineDTO(invalidDto));
    }

}
