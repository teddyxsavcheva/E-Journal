package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.DisciplineDTO;

import java.util.List;

public interface DisciplineService {

    List<DisciplineDTO> getAllDisciplines();
    DisciplineDTO getDisciplineById(Long id);
    void createDiscipline(DisciplineDTO disciplineDTO);
    void updateDiscipline(DisciplineDTO disciplineDTO, Long id);
    void deleteDiscipline(Long id);
    void validateDisciplineDTO(DisciplineDTO disciplineDTO);
}
