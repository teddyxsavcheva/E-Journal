package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.CaregiverDTO;
import com.nbu.ejournalgroupproject.dto.StudentDTO;
import com.nbu.ejournalgroupproject.model.*;
import com.nbu.ejournalgroupproject.model.user.User;
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
    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final AbsenceRepository absenceRepository;
    private final CaregiverRepository caregiverRepository;

    public StudentDTO toDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setNumberInClass(student.getNumberInClass());
        studentDTO.setSchoolClassId(student.getSchoolClass().getId());

        // Checking if the user exists
        studentDTO.setUserId(getUserId(student));

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

        // Checking if the user exists
        student.setUser(getUser(studentDTO));

        return student;
    }

    public Long getUserId(Student student) {
        return student.getUser() != null ? student.getUser().getId() : null;
    }

    public User getUser(StudentDTO studentDTO) {
        if (studentDTO.getUserId() != null) {
            return userRepository.findById(studentDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("No User found with id " + studentDTO.getUserId()));
        } else {
            return null;
        }
    }
}