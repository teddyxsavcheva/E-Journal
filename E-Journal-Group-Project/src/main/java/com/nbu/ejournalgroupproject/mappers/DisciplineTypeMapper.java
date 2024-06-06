package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.DisciplineTypeDto;
import com.nbu.ejournalgroupproject.model.DisciplineType;
import org.springframework.stereotype.Service;

@Service
public class DisciplineTypeMapper {

    public DisciplineTypeDto convertToDto(DisciplineType disciplineType) {

        DisciplineTypeDto disciplineTypeDto = new DisciplineTypeDto();

        disciplineTypeDto.setId(disciplineType.getId());
        disciplineTypeDto.setDisciplineType(disciplineType.getDisciplineTypeEnum());

        return disciplineTypeDto;
    }

    public DisciplineType convertToEntity(DisciplineTypeDto disciplineTypeDto) {

        DisciplineType disciplineType = new DisciplineType();

        disciplineType.setDisciplineTypeEnum(disciplineTypeDto.getDisciplineType());

        return disciplineType;
    }

}
