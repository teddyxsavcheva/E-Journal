package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.mappers.TeacherMapper;
import com.nbu.ejournalgroupproject.mappers.TeacherQualificationMapper;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import com.nbu.ejournalgroupproject.repository.TeacherQualificationRepository;
import com.nbu.ejournalgroupproject.repository.TeacherRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.TeacherServiceImpl;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTests {

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    TeacherQualificationRepository teacherQualificationRepository;

    @Mock
    TeacherMapper teacherMapper;

    @Mock
    TeacherQualificationMapper teacherQualificationMapper;

    @InjectMocks
    TeacherServiceImpl teacherService;

    Teacher teacher;
    TeacherDTO teacherDTO;
    TeacherQualification teacherQualification;
    TeacherQualificationDto teacherQualificationDto;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setName("Test Teacher");
        teacher.setEmail("test.teacher@example.com");
        teacher.setTeacherQualifications(new HashSet<>());

        teacherDTO = new TeacherDTO();
        teacherDTO.setName("Test Teacher");
        teacherDTO.setEmail("test.teacher@example.com");

        teacherQualification = new TeacherQualification();
        teacherQualification.setId(1L);

        teacherQualificationDto = new TeacherQualificationDto();
        teacherQualificationDto.setId(1L);
    }

    @Test
    public void teacherService_getTeacherById_returnsTeacher() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherMapper.EntityToDto(any(Teacher.class))).thenReturn(teacherDTO);

        TeacherDTO result = teacherService.getTeacherById(1L);

        assertNotNull(result);
        assertEquals("Test Teacher", result.getName());
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherMapper, times(1)).EntityToDto(any(Teacher.class));
    }

    @Test
    public void teacherService_getTeacherById_throwsException() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.getTeacherById(1L));
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherService_getTeachersFromSchool_returnsTeachers() {
        when(teacherRepository.findAllBySchoolId(1L)).thenReturn(List.of(teacher));
        when(teacherMapper.EntityToDto(any(Teacher.class))).thenReturn(teacherDTO);

        List<TeacherDTO> result = teacherService.getTeachersFromSchool(1L);

        assertEquals(1, result.size());
        verify(teacherRepository, times(1)).findAllBySchoolId(1L);
        verify(teacherMapper, times(1)).EntityToDto(any(Teacher.class));
    }

    @Test
    public void teacherService_getTeachers_returnsAllTeachers() {
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));
        when(teacherMapper.EntityToDto(any(Teacher.class))).thenReturn(teacherDTO);

        List<TeacherDTO> result = teacherService.getTeachers();

        assertEquals(1, result.size());
        verify(teacherRepository, times(1)).findAll();
        verify(teacherMapper, times(1)).EntityToDto(any(Teacher.class));
    }

    @Test
    public void teacherService_createTeacher_returnsTeacher() {
        when(teacherMapper.DtoToEntity(any(TeacherDTO.class))).thenReturn(teacher);
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        when(teacherMapper.EntityToDto(any(Teacher.class))).thenReturn(teacherDTO);

        TeacherDTO result = teacherService.createTeacher(teacherDTO);

        assertNotNull(result);
        assertEquals("Test Teacher", result.getName());
        verify(teacherMapper, times(1)).DtoToEntity(any(TeacherDTO.class));
        verify(teacherRepository, times(1)).save(any(Teacher.class));
        verify(teacherMapper, times(1)).EntityToDto(any(Teacher.class));
    }

    @Test
    public void teacherService_updateTeacher_returnsUpdatedTeacher() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        when(teacherMapper.EntityToDto(any(Teacher.class))).thenReturn(teacherDTO);

        TeacherDTO result = teacherService.updateTeacher(1L, teacherDTO);

        assertNotNull(result);
        assertEquals("Test Teacher", result.getName());
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).save(any(Teacher.class));
        verify(teacherMapper, times(1)).EntityToDto(any(Teacher.class));
    }

    @Test
    public void teacherService_updateTeacher_throwsException() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.updateTeacher(1L, teacherDTO));
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherService_deleteTeacher_returnsVoid() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        teacherService.deleteTeacher(1L);

        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).delete(any(Teacher.class));
    }

    @Test
    public void teacherService_deleteTeacher_throwsException() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.deleteTeacher(1L));
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherService_addQualificationToTeacher_returnsTeacher() {
        teacher.setTeacherQualifications(new HashSet<>());
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        when(teacherMapper.EntityToDto(any(Teacher.class))).thenReturn(teacherDTO);

        TeacherDTO result = teacherService.addQualificationToTeacher(1L, 1L);

        assertNotNull(result);
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).save(any(Teacher.class));
        verify(teacherMapper, times(1)).EntityToDto(any(Teacher.class));
    }

    @Test
    public void teacherService_addQualificationToTeacher_throwsException_whenTeacherNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.addQualificationToTeacher(1L, 1L));
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherService_addQualificationToTeacher_throwsException_whenQualificationNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.addQualificationToTeacher(1L, 1L));
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherService_deleteQualificationFromTeacher_returnsTeacher() {
        teacher.setTeacherQualifications(new HashSet<>(Set.of(teacherQualification)));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.of(teacherQualification));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        when(teacherMapper.EntityToDto(any(Teacher.class))).thenReturn(teacherDTO);

        TeacherDTO result = teacherService.deleteQualificationFromTeacher(1L, 1L);

        assertNotNull(result);
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).save(any(Teacher.class));
        verify(teacherMapper, times(1)).EntityToDto(any(Teacher.class));
    }

    @Test
    public void teacherService_deleteQualificationFromTeacher_throwsException_whenTeacherNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.deleteQualificationFromTeacher(1L, 1L));
        verify(teacherRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherService_deleteQualificationFromTeacher_throwsException_whenQualificationNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherQualificationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.deleteQualificationFromTeacher(1L, 1L));
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherQualificationRepository, times(1)).findById(1L);
    }

    @Test
    public void teacherService_getQualificationsByTeacherId_returnsQualifications() {
        teacher.setTeacherQualifications(Set.of(teacherQualification));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherQualificationMapper.convertToDto(any(TeacherQualification.class))).thenReturn(teacherQualificationDto);

        List<TeacherQualificationDto> result = teacherService.getQualificationsByTeacherId(1L);

        assertEquals(1, result.size());
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherQualificationMapper, times(1)).convertToDto(any(TeacherQualification.class));
    }

    @Test
    public void teacherService_getQualificationsByTeacherId_throwsException() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.getQualificationsByTeacherId(1L));
        verify(teacherRepository, times(1)).findById(1L);
    }
}