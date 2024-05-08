package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.SchoolDTO;
import com.nbu.ejournalgroupproject.model.School;

import java.util.List;

public interface SchoolService {
    List<SchoolDTO> getSchools();

    SchoolDTO getSchool(Long id);

    void createSchool(SchoolDTO schoolDTO);

    boolean deleteSchool(Long schoolId);



}
