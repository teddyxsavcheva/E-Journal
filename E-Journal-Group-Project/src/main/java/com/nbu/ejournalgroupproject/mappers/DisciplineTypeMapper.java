package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.DisciplineTypeDTO;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import org.springframework.stereotype.Service;

@Service
public class DisciplineTypeMapper {

    public DisciplineTypeDTO convertToDto(DisciplineType disciplineType) {

        DisciplineTypeDTO disciplineTypeDTO = new DisciplineTypeDTO();

        disciplineTypeDTO.setId(disciplineType.getId());
        disciplineTypeDTO.setDisciplineType(disciplineType.getDisciplineTypeEnum());

        return disciplineTypeDTO;
    }

    public DisciplineType convertToEntity(DisciplineTypeDTO disciplineDTO) {

        DisciplineType disciplineType = new DisciplineType();

        disciplineType.setDisciplineTypeEnum(disciplineDTO.getDisciplineType());

        return disciplineType;
    }

}
