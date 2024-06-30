package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.AbsenceDTO;
import java.util.List;

public interface AbsenceService {
    List<AbsenceDTO> getAllAbsences();
    AbsenceDTO getAbsenceById(Long id);
    AbsenceDTO createAbsence(AbsenceDTO absenceDTO);
    void deleteAbsence(Long id);
    AbsenceDTO updateAbsence(Long id, AbsenceDTO absenceDTO);
    List<Long> getAbsencesByStudentAndDiscipline(Long studentId, Long disciplineId);

    List<AbsenceDTO> getAbsenceObjectsByStudentAndDiscipline(Long studentId, Long disciplineId);
}