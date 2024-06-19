package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.SchoolDTO;

import java.util.List;

public interface SchoolService {
    List<SchoolDTO> getSchools();

    SchoolDTO getSchool(Long id);

    SchoolDTO createSchool(SchoolDTO schoolDTO);

    SchoolDTO updateSchool(Long id, SchoolDTO schoolDTO);

    void deleteSchool(Long schoolId);

    void validateSchoolDTO(SchoolDTO schoolDTO);


}
