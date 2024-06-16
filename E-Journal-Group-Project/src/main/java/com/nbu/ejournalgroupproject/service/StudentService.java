package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.StudentDTO;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(Long id);
    StudentDTO createStudent(StudentDTO studentDTO);
    void deleteStudent(Long id);
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
    StudentDTO addCaregiverToStudent(Long studentId, Long caregiverId);
    StudentDTO removeCaregiverFromStudent(Long studentId, Long caregiverId);
}
