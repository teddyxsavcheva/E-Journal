package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;

import java.util.List;

public interface SchoolClassService {
    List<SchoolClassDTO> getSchoolClasses();

    SchoolClassDTO getSchoolClas(Long id);

    SchoolClassDTO createSchoolClass(SchoolClassDTO schoolClassDTO);

    SchoolClassDTO updateSchoolClass(Long id, SchoolClassDTO schoolClassDTO);

    void deleteSchoolClass(Long id);

    void validateSchoolClassDTO(SchoolClassDTO schoolClassDTO);

}
