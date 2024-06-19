package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.SchoolTypeDTO;
import java.util.List;

public interface SchoolTypeService {
    List<SchoolTypeDTO> getAllSchoolTypes();

    SchoolTypeDTO getSchoolTypeById(Long id);

    SchoolTypeDTO createSchoolType(SchoolTypeDTO schoolTypeDTO);

    SchoolTypeDTO updateSchoolType(Long id, SchoolTypeDTO schoolTypeDTO);

    void deleteSchoolType(Long id);

//    void validateSchoolTypeDTO(SchoolTypeDTO schoolTypeDTO);
}
