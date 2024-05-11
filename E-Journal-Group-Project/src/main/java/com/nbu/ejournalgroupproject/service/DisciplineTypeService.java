package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.DisciplineTypeDTO;

import java.util.List;

public interface DisciplineTypeService {

    List<DisciplineTypeDTO> getAllDisciplineTypes();

    DisciplineTypeDTO getDisciplineTypeById(Long id);

    void createDisciplineType(DisciplineTypeDTO disciplineTypeDTO);

    void updateDisciplineType(DisciplineTypeDTO disciplineTypeDTO, Long id);

    void deleteDisciplineType(Long id);
}
