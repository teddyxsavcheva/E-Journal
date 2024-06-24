package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.StudentDTO;
import com.nbu.ejournalgroupproject.model.*;
import com.nbu.ejournalgroupproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudentMapper {

    private final SchoolClassRepository schoolClassRepository;
    private final GradeRepository gradeRepository;
    private final AbsenceRepository absenceRepository;
    private final CaregiverRepository caregiverRepository;

    public StudentDTO toDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setNumberInClass(student.getNumberInClass());
        studentDTO.setSchoolClassId(student.getSchoolClass().getId());
        return studentDTO;
    }

    public Student toEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setNumberInClass(studentDTO.getNumberInClass());
        SchoolClass schoolClass = schoolClassRepository.findById(studentDTO.getSchoolClassId())
                .orElseThrow(() -> new EntityNotFoundException("SchoolClass not found with id " + studentDTO.getSchoolClassId()));
        student.setSchoolClass(schoolClass);
        return student;
    }
}