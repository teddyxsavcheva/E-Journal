package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.AbsenceDTO;
import com.nbu.ejournalgroupproject.model.*;
import com.nbu.ejournalgroupproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AbsenceMapper {

    private final StudentRepository studentRepository;
    private final DisciplineRepository disciplineRepository;
    private final AbsenceTypeRepository absenceTypeRepository;
    private final AbsenceStatusRepository absenceStatusRepository;

    public AbsenceDTO toDTO(Absence absence) {
        AbsenceDTO absenceDTO = new AbsenceDTO();
        absenceDTO.setId(absence.getId());
        absenceDTO.setDateOfIssue(absence.getDateOfIssue());
        absenceDTO.setStudentId(absence.getStudent().getId());
        absenceDTO.setDisciplineId(absence.getDiscipline().getId());
        absenceDTO.setAbsenceTypeId(absence.getAbsenceType().getId());
        absenceDTO.setAbsenceStatusId(absence.getAbsenceStatus().getId());
        return absenceDTO;
    }

    public Absence toEntity(AbsenceDTO absenceDTO) {
        Absence absence = new Absence();
        absence.setId(absenceDTO.getId());
        absence.setDateOfIssue(absenceDTO.getDateOfIssue());

        Student student = studentRepository.findById(absenceDTO.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + absenceDTO.getStudentId()));
        Discipline discipline = disciplineRepository.findById(absenceDTO.getDisciplineId())
                .orElseThrow(() -> new EntityNotFoundException("Discipline not found with id " + absenceDTO.getDisciplineId()));
        AbsenceType absenceType = absenceTypeRepository.findById(absenceDTO.getAbsenceTypeId())
                .orElseThrow(() -> new EntityNotFoundException("AbsenceType not found with id " + absenceDTO.getAbsenceTypeId()));
        AbsenceStatus absenceStatus = absenceStatusRepository.findById(absenceDTO.getAbsenceStatusId())
                .orElseThrow(() -> new EntityNotFoundException("AbsenceStatus not found with id " + absenceDTO.getAbsenceStatusId()));

        absence.setStudent(student);
        absence.setDiscipline(discipline);
        absence.setAbsenceType(absenceType);
        absence.setAbsenceStatus(absenceStatus);

        return absence;
    }
}