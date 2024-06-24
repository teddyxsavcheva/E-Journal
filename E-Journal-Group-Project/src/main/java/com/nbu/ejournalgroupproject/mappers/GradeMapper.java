package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.GradeDTO;
import com.nbu.ejournalgroupproject.model.Grade;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.GradeType;
import com.nbu.ejournalgroupproject.repository.StudentRepository;
import com.nbu.ejournalgroupproject.repository.DisciplineRepository;
import com.nbu.ejournalgroupproject.repository.GradeTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GradeMapper {

    private final StudentRepository studentRepository;
    private final DisciplineRepository disciplineRepository;
    private final GradeTypeRepository gradeTypeRepository;

    public GradeDTO toDTO(Grade grade) {
        GradeDTO gradeDTO = new GradeDTO();
        gradeDTO.setId(grade.getId());
        gradeDTO.setDateOfIssue(grade.getDateOfIssue());
        gradeDTO.setStudentId(grade.getStudent().getId());
        gradeDTO.setDisciplineId(grade.getDiscipline().getId());
        gradeDTO.setGradeTypeId(grade.getGradeType().getId());
        return gradeDTO;
    }

    public Grade toEntity(GradeDTO gradeDTO) {
        Grade grade = new Grade();
        grade.setId(gradeDTO.getId());
        grade.setDateOfIssue(gradeDTO.getDateOfIssue());

        Student student = studentRepository.findById(gradeDTO.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + gradeDTO.getStudentId()));
        Discipline discipline = disciplineRepository.findById(gradeDTO.getDisciplineId())
                .orElseThrow(() -> new EntityNotFoundException("Discipline not found with id " + gradeDTO.getDisciplineId()));
        GradeType gradeType = gradeTypeRepository.findById(gradeDTO.getGradeTypeId())
                .orElseThrow(() -> new EntityNotFoundException("GradeType not found with id " + gradeDTO.getGradeTypeId()));

        grade.setStudent(student);
        grade.setDiscipline(discipline);
        grade.setGradeType(gradeType);

        return grade;
    }
}