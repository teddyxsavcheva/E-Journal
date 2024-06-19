package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.DisciplineTypeDto;

import java.util.List;

public interface DisciplineTypeService {

    List<DisciplineTypeDto> getAllDisciplineTypes();

    DisciplineTypeDto getDisciplineTypeById(Long id);

    DisciplineTypeDto createDisciplineType(DisciplineTypeDto disciplineTypeDTO);

    DisciplineTypeDto updateDisciplineType(DisciplineTypeDto disciplineTypeDTO, Long id);

    void deleteDisciplineType(Long id);

    void validateDisciplineTypeDTO(DisciplineTypeDto disciplineTypeDTO);

}
