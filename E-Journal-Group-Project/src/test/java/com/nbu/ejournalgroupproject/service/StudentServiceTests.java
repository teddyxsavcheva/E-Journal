package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.StudentDTO;
import com.nbu.ejournalgroupproject.mappers.StudentMapper;
import com.nbu.ejournalgroupproject.model.Caregiver;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.repository.CaregiverRepository;
import com.nbu.ejournalgroupproject.repository.StudentRepository;
import com.nbu.ejournalgroupproject.service.serviceImpl.StudentServiceImpl;
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
public class StudentServiceTests {

    @Mock
    StudentRepository studentRepository;

    @Mock
    CaregiverRepository caregiverRepository;

    @Mock
    StudentMapper studentMapper;

    @InjectMocks
    StudentServiceImpl studentService;

    Student student;
    StudentDTO studentDTO;
    Caregiver caregiver;

    @BeforeEach
    void setUp() {
        caregiver = new Caregiver();
        caregiver.setId(1L);
        caregiver.setName("Test Caregiver");

        student = new Student();
        student.setId(1L);
        student.setName("Test Student");
        student.setNumberInClass(1);
        student.setCaregivers(new HashSet<>());  // Initialize with an empty HashSet

        studentDTO = new StudentDTO();
        studentDTO.setId(1L);
        studentDTO.setName("Test Student");
        studentDTO.setNumberInClass(1);
    }

    @Test
    public void studentService_getAllStudents_returnsAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));
        when(studentMapper.toDTO(any(Student.class))).thenReturn(studentDTO);

        List<StudentDTO> result = studentService.getAllStudents();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Student", result.get(0).getName());
        verify(studentRepository, times(1)).findAll();
        verify(studentMapper, times(1)).toDTO(any(Student.class));
    }

    @Test
    public void studentService_getStudentById_returnsStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.toDTO(any(Student.class))).thenReturn(studentDTO);

        StudentDTO result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals("Test Student", result.getName());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentMapper, times(1)).toDTO(any(Student.class));
    }

    @Test
    public void studentService_getStudentById_throwsException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.getStudentById(1L));
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void studentService_createStudent_returnsStudent() {
        when(studentMapper.toEntity(any(StudentDTO.class))).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toDTO(any(Student.class))).thenReturn(studentDTO);

        StudentDTO result = studentService.createStudent(studentDTO);

        assertNotNull(result);
        assertEquals("Test Student", result.getName());
        verify(studentMapper, times(1)).toEntity(any(StudentDTO.class));
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(studentMapper, times(1)).toDTO(any(Student.class));
    }

    @Test
    public void studentService_updateStudent_returnsStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.toEntity(any(StudentDTO.class))).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toDTO(any(Student.class))).thenReturn(studentDTO);

        StudentDTO result = studentService.updateStudent(1L, studentDTO);

        assertNotNull(result);
        assertEquals("Test Student", result.getName());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentMapper, times(1)).toEntity(any(StudentDTO.class));
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(studentMapper, times(1)).toDTO(any(Student.class));
    }

    @Test
    public void studentService_updateStudent_throwsException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.updateStudent(1L, studentDTO));
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void studentService_deleteStudent_deletesStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void studentService_addCaregiverToStudent_addsCaregiver() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(caregiverRepository.findById(1L)).thenReturn(Optional.of(caregiver));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toDTO(any(Student.class))).thenReturn(studentDTO);

        StudentDTO result = studentService.addCaregiverToStudent(1L, 1L);

        assertNotNull(result);
        assertTrue(student.getCaregivers().contains(caregiver));
        verify(studentRepository, times(1)).findById(1L);
        verify(caregiverRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(studentMapper, times(1)).toDTO(any(Student.class));
    }

    @Test
    public void studentService_removeCaregiverFromStudent_removesCaregiver() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(caregiverRepository.findById(1L)).thenReturn(Optional.of(caregiver));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentMapper.toDTO(any(Student.class))).thenReturn(studentDTO);

        StudentDTO result = studentService.removeCaregiverFromStudent(1L, 1L);

        assertNotNull(result);
        assertFalse(student.getCaregivers().contains(caregiver));
        verify(studentRepository, times(1)).findById(1L);
        verify(caregiverRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(studentMapper, times(1)).toDTO(any(Student.class));
    }

    @Test
    public void studentService_getStudentsFromClass_returnsStudents() {
        when(studentRepository.getStudentsBySchoolClassId(1L)).thenReturn(List.of(student));
        when(studentMapper.toDTO(any(Student.class))).thenReturn(studentDTO);

        List<StudentDTO> result = studentService.getStudentsFromClass(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Student", result.get(0).getName());
        verify(studentRepository, times(1)).getStudentsBySchoolClassId(1L);
        verify(studentMapper, times(1)).toDTO(any(Student.class));
    }
}