package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.StudentDTO;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.model.Grade;
import com.nbu.ejournalgroupproject.model.Absence;
import com.nbu.ejournalgroupproject.model.Caregiver;
import java.util.stream.Collectors;



import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentDTO toDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setNumberInClass(student.getNumberInClass());
        dto.setSchoolClassId(student.getSchoolClass().getId());
        dto.setGradeIds(student.getGrades().stream().map(Grade::getId).collect(Collectors.toList()));
        dto.setAbsenceIds(student.getAbsences().stream().map(Absence::getId).collect(Collectors.toList()));
        dto.setCaregiverIds(student.getCaregivers().stream().map(Caregiver::getId).collect(Collectors.toList()));
        return dto;
    }

    public Student toEntity(StudentDTO dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setName(dto.getName());
        student.setNumberInClass(dto.getNumberInClass());
        // Relationships should be set in the service layer
        return student;
    }
}
