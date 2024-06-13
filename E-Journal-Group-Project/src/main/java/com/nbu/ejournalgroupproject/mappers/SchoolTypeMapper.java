package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.SchoolTypeDTO;
import com.nbu.ejournalgroupproject.model.SchoolType;
import org.springframework.stereotype.Service;

@Service
public class SchoolTypeMapper {

    public SchoolType mapDtoToEntity(SchoolTypeDTO schoolTypeDTO){
        SchoolType schoolType = new SchoolType();
        schoolType.setId(schoolTypeDTO.getId());
        schoolType.setSchoolTypeEnum(schoolTypeDTO.getSchoolType());
        return schoolType;
    }

    public SchoolTypeDTO mapEntityToDto(SchoolType schoolType){
        SchoolTypeDTO schoolTypeDTO = new SchoolTypeDTO();
        schoolTypeDTO.setId(schoolType.getId());
        schoolTypeDTO.setSchoolType(schoolType.getSchoolTypeEnum());
        return schoolTypeDTO;
    }
}
