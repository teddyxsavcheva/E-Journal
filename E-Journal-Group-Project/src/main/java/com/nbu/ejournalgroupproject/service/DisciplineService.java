package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.DisciplineDto;

import java.util.List;

public interface DisciplineService {

    List<DisciplineDto> getAllDisciplines();

    DisciplineDto getDisciplineById(Long id);

    DisciplineDto createDiscipline(DisciplineDto disciplineDTO);

    DisciplineDto updateDiscipline(DisciplineDto disciplineDTO, Long id);

    void deleteDiscipline(Long id);

    void validateDisciplineDTO(DisciplineDto disciplineDTO);

    DisciplineDto addQualificationToDiscipline(Long disciplineId, Long qualificationId);

    DisciplineDto removeQualificationFromDiscipline(Long disciplineId, Long qualificationId);

    List<DisciplineDto> getDisciplinesByStudentId(Long studentId);
}
