package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.StudentCurriculumDTO;
import com.nbu.ejournalgroupproject.mappers.StudentCurriculumMapper;
import com.nbu.ejournalgroupproject.model.SchoolClass;
import com.nbu.ejournalgroupproject.model.StudentCurriculum;
import com.nbu.ejournalgroupproject.repository.SchoolClassRepository;
import com.nbu.ejournalgroupproject.repository.StudentCurriculumRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.StudentCurriculumServiceImpl;
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
public class StudentCurriculumServiceTests {

    @Mock
    StudentCurriculumRepository studentCurriculumRepository;

    @Mock
    SchoolClassRepository schoolClassRepository;

    @Mock
    StudentCurriculumMapper studentCurriculumMapper;

    @InjectMocks
    StudentCurriculumServiceImpl studentCurriculumService;

    StudentCurriculum studentCurriculum;
    StudentCurriculumDTO studentCurriculumDTO;
    SchoolClass schoolClass;

    @BeforeEach
    void setUp() {
        schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("Test Class");
        schoolClass.setYear(2023);

        studentCurriculum = new StudentCurriculum();
        studentCurriculum.setId(1L);
        studentCurriculum.setYear(2023);
        studentCurriculum.setSemester(1);
        studentCurriculum.setSchoolClass(schoolClass);

        studentCurriculumDTO = new StudentCurriculumDTO();
        studentCurriculumDTO.setId(1L);
        studentCurriculumDTO.setYear(2023);
        studentCurriculumDTO.setSemester(1);
        studentCurriculumDTO.setSchoolClassId(1L);
    }

    @Test
    public void studentCurriculumService_getStudentCurriculums_returnsStudentCurriculums() {
        when(studentCurriculumRepository.findAll()).thenReturn(List.of(studentCurriculum));
        when(studentCurriculumMapper.entityToDto(any(StudentCurriculum.class))).thenReturn(studentCurriculumDTO);

        List<StudentCurriculumDTO> result = studentCurriculumService.getStudentCurriculums();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2023, result.get(0).getYear());
        verify(studentCurriculumRepository, times(1)).findAll();
        verify(studentCurriculumMapper, times(1)).entityToDto(any(StudentCurriculum.class));
    }

    @Test
    public void studentCurriculumService_getStudentCurriculum_returnsStudentCurriculum() {
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.of(studentCurriculum));
        when(studentCurriculumMapper.entityToDto(any(StudentCurriculum.class))).thenReturn(studentCurriculumDTO);

        StudentCurriculumDTO result = studentCurriculumService.getStudentCurriculum(1L);

        assertNotNull(result);
        assertEquals(2023, result.getYear());
        verify(studentCurriculumRepository, times(1)).findById(1L);
        verify(studentCurriculumMapper, times(1)).entityToDto(any(StudentCurriculum.class));
    }

    @Test
    public void studentCurriculumService_getStudentCurriculum_throwsException() {
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentCurriculumService.getStudentCurriculum(1L));
        verify(studentCurriculumRepository, times(1)).findById(1L);
    }

    @Test
    public void studentCurriculumService_createStudentCurriculum_returnsStudentCurriculum() {
        when(studentCurriculumMapper.DtoToEntity(any(StudentCurriculumDTO.class))).thenReturn(studentCurriculum);
        when(studentCurriculumRepository.save(any(StudentCurriculum.class))).thenReturn(studentCurriculum);
        when(studentCurriculumMapper.entityToDto(any(StudentCurriculum.class))).thenReturn(studentCurriculumDTO);

        StudentCurriculumDTO result = studentCurriculumService.createStudentCurriculum(studentCurriculumDTO);

        assertNotNull(result);
        assertEquals(2023, result.getYear());
        verify(studentCurriculumMapper, times(1)).DtoToEntity(any(StudentCurriculumDTO.class));
        verify(studentCurriculumRepository, times(1)).save(any(StudentCurriculum.class));
        verify(studentCurriculumMapper, times(1)).entityToDto(any(StudentCurriculum.class));
    }

    @Test
    public void studentCurriculumService_updateStudentCurriculum_returnsStudentCurriculum() {
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.of(studentCurriculum));
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(studentCurriculumRepository.save(any(StudentCurriculum.class))).thenReturn(studentCurriculum);
        when(studentCurriculumMapper.entityToDto(any(StudentCurriculum.class))).thenReturn(studentCurriculumDTO);

        StudentCurriculumDTO result = studentCurriculumService.updateStudentCurriculum(1L, studentCurriculumDTO);

        assertNotNull(result);
        assertEquals(2023, result.getYear());
        verify(studentCurriculumRepository, times(1)).findById(1L);
        verify(schoolClassRepository, times(1)).findById(1L);
        verify(studentCurriculumRepository, times(1)).save(any(StudentCurriculum.class));
        verify(studentCurriculumMapper, times(1)).entityToDto(any(StudentCurriculum.class));
    }

    @Test
    public void studentCurriculumService_updateStudentCurriculum_throwsException() {
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentCurriculumService.updateStudentCurriculum(1L, studentCurriculumDTO));
        verify(studentCurriculumRepository, times(1)).findById(1L);
    }

    @Test
    public void studentCurriculumService_deleteStudentCurriculum_returnsVoid() {
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.of(studentCurriculum));

        studentCurriculumService.deleteStudentCurriculum(1L);

        verify(studentCurriculumRepository, times(1)).findById(1L);
        verify(studentCurriculumRepository, times(1)).delete(any(StudentCurriculum.class));
    }

    @Test
    public void studentCurriculumService_deleteStudentCurriculum_throwsException() {
        when(studentCurriculumRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentCurriculumService.deleteStudentCurriculum(1L));
        verify(studentCurriculumRepository, times(1)).findById(1L);
    }

    @Test
    public void studentCurriculumService_validateStudentCurriculumDTO_throwsException_whenSchoolClassIdIsZero() {
        StudentCurriculumDTO invalidStudentCurriculumDTO = new StudentCurriculumDTO();
        invalidStudentCurriculumDTO.setSchoolClassId(0L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> studentCurriculumService.validateStudentCurriculumDTO(invalidStudentCurriculumDTO));
        assertEquals("The School class cannot be zero.", exception.getMessage());
    }

    @Test
    public void studentCurriculumService_getStudentCurriculumByClassId_returnsStudentCurriculum() {
        when(studentCurriculumRepository.getStudentCurriculumBySchoolClassId(1L)).thenReturn(studentCurriculum);
        when(studentCurriculumMapper.entityToDto(any(StudentCurriculum.class))).thenReturn(studentCurriculumDTO);

        StudentCurriculumDTO result = studentCurriculumService.getStudentCurriculumByClassId(1L);

        assertNotNull(result);
        assertEquals(2023, result.getYear());
        verify(studentCurriculumRepository, times(1)).getStudentCurriculumBySchoolClassId(1L);
        verify(studentCurriculumMapper, times(1)).entityToDto(any(StudentCurriculum.class));
    }
}