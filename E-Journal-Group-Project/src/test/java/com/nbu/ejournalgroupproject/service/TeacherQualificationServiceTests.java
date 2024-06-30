package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import com.nbu.ejournalgroupproject.mappers.TeacherQualificationMapper;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import com.nbu.ejournalgroupproject.repository.DisciplineRepository;
import com.nbu.ejournalgroupproject.repository.DisciplineTypeRepository;
import com.nbu.ejournalgroupproject.repository.TeacherQualificationRepository;
import com.nbu.ejournalgroupproject.repository.TeacherRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.TeacherQualificationServiceImpl;
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
public class TeacherQualificationServiceTests {

    @Mock
    TeacherQualificationRepository teacherQualificationRepository;

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    DisciplineTypeRepository disciplineTypeRepository;

    @Mock
    DisciplineRepository disciplineRepository;

    @Mock
    TeacherQualificationMapper teacherQualificationMapper;

    @InjectMocks
    TeacherQualificationServiceImpl teacherQualificationService;

    TeacherQualification teacherQualification;
    TeacherQualificationDto teacherQualificationDto;
    Discipline discipline;
    DisciplineType disciplineType;
    Teacher teacher;

    @BeforeEach
    void setUp() {
        teacherQualification = new TeacherQualification();
        teacherQualification.setQualificationEnum(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING);
        Set<Discipline> disciplines = new HashSet<>();
        teacherQualification.setDisciplines(disciplines);
        Set<Teacher> teachers = new HashSet<>();
        teacherQualification.setTeachers(teachers);

        teacherQualificationDto = new TeacherQualificationDto();
        teacherQualificationDto.setQualificationEnum(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING);

        disciplineType = new DisciplineType();
        disciplineType.setDisciplineTypeEnum(DisciplineTypeEnum.BIOLOGY);
        disciplineTypeRepository.save(disciplineType);

        discipline = new Discipline();
        discipline.setName("Biology");
        discipline.setDisciplineType(disciplineType);
        Set<TeacherQualification> qualificationSet = new HashSet<>();
        discipline.setTeacherQualifications(qualificationSet);

        teacher = new Teacher();
        teacher.setName("Teddy");
        teacher.setEmail("teddyeqka@example.com");
        Set<TeacherQualification> teacherQualifications = new HashSet<>();
        teacher.setTeacherQualifications(teacherQualifications);
        teacherRepository.save(teacher);
    }

    @AfterEach
    void tearDown() {
        teacherQualificationRepository.deleteAll();
    }

    @Test
    public void teacherQualificationService_getAllTeacherQualifications_returnsAllTeacherQualifications() {
        when(teacherQualificationRepository.findAll()).thenReturn(List.of(teacherQualification));
        when(teacherQualificationMapper.convertToDto(any(TeacherQualification.class))).thenReturn(teacherQualificationDto);

        List<TeacherQualificationDto> result = teacherQualificationService.getAllTeacherQualifications();

        assertEquals(1, result.size());
        verify(teacherQualificationRepository, times(1)).findAll();
        verify(teacherQualificationMapper, times(1)).convertToDto(any(TeacherQualification.class));
    }

    @Test
    public void teacherQualificationService_getTeacherQualificationById_returnsTeacherQualification() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));
        when(teacherQualificationMapper.convertToDto(any(TeacherQualification.class))).thenReturn(teacherQualificationDto);

        TeacherQualificationDto result = teacherQualificationService.getTeacherQualificationById(1L);

        assertNotNull(result);
        assertEquals(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, result.getQualificationEnum());
        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(teacherQualificationMapper, times(1)).convertToDto(any(TeacherQualification.class));
    }

    @Test
    public void teacherQualificationService_getTeacherQualificationById_throwsException() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherQualificationService.getTeacherQualificationById(1L));
        verify(teacherQualificationRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherQualificationService_createTeacherQualification_returnsTeacherQualification() {
        when(teacherQualificationMapper.convertToEntity(any(TeacherQualificationDto.class))).thenReturn(teacherQualification);
        when(teacherQualificationRepository.save(any(TeacherQualification.class))).thenReturn(teacherQualification);
        when(teacherQualificationMapper.convertToDto(any(TeacherQualification.class))).thenReturn(teacherQualificationDto);

        TeacherQualificationDto result = teacherQualificationService.createTeacherQualification(teacherQualificationDto);

        assertNotNull(result);
        assertEquals(TeacherQualificationEnum.PERMIT_BIOLOGY_TEACHING, result.getQualificationEnum());
        verify(teacherQualificationMapper, times(1)).convertToEntity(any(TeacherQualificationDto.class));
        verify(teacherQualificationRepository, times(1)).save(any(TeacherQualification.class));
        verify(teacherQualificationMapper, times(1)).convertToDto(any(TeacherQualification.class));
    }

    @Test
    public void teacherQualificationService_updateTeacherQualification_returnsTeacherQualification() {
        TeacherQualificationDto newDto = new TeacherQualificationDto();
        newDto.setQualificationEnum(TeacherQualificationEnum.PERMIT_CHEMISTRY_TEACHING);

        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));
        when(teacherQualificationRepository.save(any(TeacherQualification.class))).thenReturn(teacherQualification);
        when(teacherQualificationMapper.convertToDto(any(TeacherQualification.class))).thenReturn(newDto);

        TeacherQualificationDto result = teacherQualificationService.updateTeacherQualification(newDto, 1L);

        assertNotNull(result);
        assertEquals(TeacherQualificationEnum.PERMIT_CHEMISTRY_TEACHING, result.getQualificationEnum());
        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).save(any(TeacherQualification.class));
        verify(teacherQualificationMapper, times(1)).convertToDto(any(TeacherQualification.class));
    }

    @Test
    public void teacherQualificationService_updateTeacherQualification_throwsException() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherQualificationService.updateTeacherQualification(teacherQualificationDto, 1L));
        verify(teacherQualificationRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherQualificationService_deleteTeacherQualification_returnsVoid() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));

        teacherQualificationService.deleteTeacherQualification(1L);

        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).delete(any(TeacherQualification.class));
    }

    @Test
    public void teacherQualificationService_deleteTeacherQualification_throwsException() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherQualificationService.deleteTeacherQualification(1L));
        verify(teacherQualificationRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherQualificationService_addTeacherToQualification_returnsTeacherQualification() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherQualificationMapper.convertToDto(any(TeacherQualification.class))).thenReturn(teacherQualificationDto);

        TeacherQualificationDto result = teacherQualificationService.addTeacherToQualification(1L, 1L);

        assertNotNull(result);
        assertTrue(teacherQualification.getTeachers().contains(teacher));
        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).save(any(TeacherQualification.class));
        verify(teacherQualificationMapper, times(1)).convertToDto(any(TeacherQualification.class));
    }

    @Test
    public void teacherQualificationService_addTeacherToQualification_throwsException() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherQualificationService.addTeacherToQualification(1L, 1L));
        verify(teacherQualificationRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherQualificationService_removeTeacherFromQualification_returnsTeacherQualification() {
        teacherQualification.getTeachers().add(teacher);
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherQualificationMapper.convertToDto(any(TeacherQualification.class))).thenReturn(teacherQualificationDto);

        TeacherQualificationDto result = teacherQualificationService.removeTeacherFromQualification(1L, 1L);

        assertNotNull(result);
        assertFalse(teacherQualification.getTeachers().contains(teacher));
        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).save(any(TeacherQualification.class));
        verify(teacherQualificationMapper, times(1)).convertToDto(any(TeacherQualification.class));
    }

    @Test
    public void teacherQualificationService_removeTeacherFromQualification_throwsException() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherQualificationService.removeTeacherFromQualification(1L, 1L));
        verify(teacherQualificationRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherQualificationService_addDisciplineToQualification_returnsTeacherQualification() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(teacherQualificationMapper.convertToDto(any(TeacherQualification.class))).thenReturn(teacherQualificationDto);

        TeacherQualificationDto result = teacherQualificationService.addDisciplineToQualification(1L, 1L);

        assertNotNull(result);
        assertTrue(teacherQualification.getDisciplines().contains(discipline));
        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).save(any(TeacherQualification.class));
        verify(teacherQualificationMapper, times(1)).convertToDto(any(TeacherQualification.class));
    }

    @Test
    public void teacherQualificationService_addDisciplineToQualification_throwsException() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherQualificationService.addDisciplineToQualification(1L, 1L));
        verify(teacherQualificationRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherQualificationService_removeDisciplineFromQualification_returnsTeacherQualification() {
        teacherQualification.getDisciplines().add(discipline);
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));
        when(disciplineRepository.findById(1L)).thenReturn(Optional.of(discipline));
        when(teacherQualificationMapper.convertToDto(any(TeacherQualification.class))).thenReturn(teacherQualificationDto);

        TeacherQualificationDto result = teacherQualificationService.removeDisciplineFromQualification(1L, 1L);

        assertNotNull(result);
        assertFalse(teacherQualification.getDisciplines().contains(discipline));
        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(disciplineRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).save(any(TeacherQualification.class));
        verify(teacherQualificationMapper, times(1)).convertToDto(any(TeacherQualification.class));
    }

    @Test
    public void teacherQualificationService_removeDisciplineFromQualification_throwsException() {
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherQualificationService.removeDisciplineFromQualification(1L, 1L));
        verify(teacherQualificationRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherQualificationService_validateTeacherQualificationDto_throwsException_whenQualificationEnumIsNull() {
        TeacherQualificationDto invalidDto = new TeacherQualificationDto();

        assertThrows(IllegalArgumentException.class, () -> teacherQualificationService.validateTeacherQualificationDto(invalidDto));
    }

}
