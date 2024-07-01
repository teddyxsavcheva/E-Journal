package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.GradeDTO;
import com.nbu.ejournalgroupproject.enums.GradeTypeEnum;
import com.nbu.ejournalgroupproject.mappers.GradeMapper;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.Grade;
import com.nbu.ejournalgroupproject.model.GradeType;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.repository.GradeRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.GradeServiceImpl;
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
public class GradeServiceTests {

    @Mock
    private GradeRepository gradeRepository;

    @Mock
    private GradeMapper gradeMapper;

    @InjectMocks
    private GradeServiceImpl gradeService;

    private Grade grade;
    private GradeDTO gradeDTO;
    private GradeType gradeType;
    private Student student;
    private Discipline discipline;

    @BeforeEach
    void setUp() {
        gradeType = new GradeType();
        gradeType.setId(1L);
        gradeType.setGradeTypeEnum(GradeTypeEnum.A);

        student = new Student();
        student.setId(1L);

        discipline = new Discipline();
        discipline.setId(1L);

        grade = new Grade();
        grade.setId(1L);
        grade.setDateOfIssue(LocalDate.now());
        grade.setStudent(student);
        grade.setDiscipline(discipline);
        grade.setGradeType(gradeType);

        gradeDTO = new GradeDTO();
        gradeDTO.setId(1L);
        gradeDTO.setDateOfIssue(LocalDate.now());
        gradeDTO.setStudentId(1L);
        gradeDTO.setDisciplineId(1L);
        gradeDTO.setGradeTypeId(1L);
    }

    @Test
    public void gradeService_getAllGrades_returnsAllGrades() {
        when(gradeRepository.findAll()).thenReturn(List.of(grade));
        when(gradeMapper.toDTO(any(Grade.class))).thenReturn(gradeDTO);

        List<GradeDTO> result = gradeService.getAllGrades();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getGradeTypeId());
        verify(gradeRepository, times(1)).findAll();
        verify(gradeMapper, times(1)).toDTO(any(Grade.class));
    }

    @Test
    public void gradeService_getGradeById_returnsGrade() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(gradeMapper.toDTO(any(Grade.class))).thenReturn(gradeDTO);

        GradeDTO result = gradeService.getGradeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getGradeTypeId());
        verify(gradeRepository, times(1)).findById(1L);
        verify(gradeMapper, times(1)).toDTO(any(Grade.class));
    }

    @Test
    public void gradeService_getGradeById_throwsException() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gradeService.getGradeById(1L));
        verify(gradeRepository, times(1)).findById(1L);
    }

    @Test
    public void gradeService_createGrade_returnsGrade() {
        when(gradeMapper.toEntity(any(GradeDTO.class))).thenReturn(grade);
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);
        when(gradeMapper.toDTO(any(Grade.class))).thenReturn(gradeDTO);

        GradeDTO result = gradeService.createGrade(gradeDTO);

        assertNotNull(result);
        assertEquals(1L, result.getGradeTypeId());
        verify(gradeMapper, times(1)).toEntity(any(GradeDTO.class));
        verify(gradeRepository, times(1)).save(any(Grade.class));
        verify(gradeMapper, times(1)).toDTO(any(Grade.class));
    }

    @Test
    public void gradeService_updateGrade_returnsGrade() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(gradeMapper.toEntity(any(GradeDTO.class))).thenReturn(grade);
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);
        when(gradeMapper.toDTO(any(Grade.class))).thenReturn(gradeDTO);

        GradeDTO result = gradeService.updateGrade(1L, gradeDTO);

        assertNotNull(result);
        assertEquals(1L, result.getGradeTypeId());
        verify(gradeRepository, times(1)).findById(1L);
        verify(gradeMapper, times(1)).toEntity(any(GradeDTO.class));
        verify(gradeRepository, times(1)).save(any(Grade.class));
        verify(gradeMapper, times(1)).toDTO(any(Grade.class));
    }

    @Test
    public void gradeService_updateGrade_throwsException() {
        when(gradeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gradeService.updateGrade(1L, gradeDTO));
        verify(gradeRepository, times(1)).findById(1L);
    }

    @Test
    public void gradeService_deleteGrade_deletesGrade() {
        doNothing().when(gradeRepository).deleteById(1L);

        gradeService.deleteGrade(1L);

        verify(gradeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void gradeService_getGradesByStudentAndDiscipline_returnsGrades() {
        when(gradeRepository.findGradeTypesByStudentAndDiscipline(1L, 1L)).thenReturn(List.of("A"));

        List<String> result = gradeService.getGradesByStudentAndDiscipline(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("A", result.get(0));
        verify(gradeRepository, times(1)).findGradeTypesByStudentAndDiscipline(1L, 1L);
    }

    @Test
    public void gradeService_findAvgGradeForSchool_returnsGrades() {
        when(gradeRepository.findAvgGradeForSchool(1L)).thenReturn(List.of("A"));

        List<String> result = gradeService.findAvgGradeForSchool(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("A", result.get(0));
        verify(gradeRepository, times(1)).findAvgGradeForSchool(1L);
    }

    @Test
    public void gradeService_findAvgGradeForDiscipline_returnsGrades() {
        when(gradeRepository.findAvgGradeForDiscipline(1L)).thenReturn(List.of("A"));

        List<String> result = gradeService.findAvgGradeForDiscipline(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("A", result.get(0));
        verify(gradeRepository, times(1)).findAvgGradeForDiscipline(1L);
    }

    @Test
    public void gradeService_findAvgGradeForTeacherByDiscipline_returnsGrades() {
        when(gradeRepository.findAvgGradeForTeacherByDiscipline(1L)).thenReturn(List.of("A"));

        List<String> result = gradeService.findAvgGradeForTeacherByDiscipline(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("A", result.get(0));
        verify(gradeRepository, times(1)).findAvgGradeForTeacherByDiscipline(1L);
    }

    @Test
    public void gradeService_getGradeObjectsByStudentAndDiscipline_returnsGrades() {
        when(gradeRepository.getAllByStudentIdAndDisciplineId(1L, 1L)).thenReturn(List.of(grade));
        when(gradeMapper.toDTO(any(Grade.class))).thenReturn(gradeDTO);

        List<GradeDTO> result = gradeService.getGradeObjectsByStudentAndDiscipline(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getGradeTypeId());
        verify(gradeRepository, times(1)).getAllByStudentIdAndDisciplineId(1L, 1L);
        verify(gradeMapper, times(1)).toDTO(any(Grade.class));
    }
}