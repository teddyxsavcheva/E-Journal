package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.StudentDTO;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(Long id);
    StudentDTO saveStudent(StudentDTO studentDTO);
    void deleteStudent(Long id);
}
