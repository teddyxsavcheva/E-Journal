package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.HeadmasterDTO;
import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;

import java.util.List;

public interface HeadmasterService {
    List<HeadmasterDTO> getHeadmasters();

    HeadmasterDTO getHeadmaster(Long id);

    HeadmasterDTO getHeadmasterBySchoolID(Long id);

    HeadmasterDTO createHeadmaster(HeadmasterDTO headmasterDTO);

    void deleteHeadmaster(Long id);

    HeadmasterDTO updateHeadmaster(Long id, HeadmasterDTO headmasterDTO);

    void validateHeadmasterDTO(HeadmasterDTO headmasterDTO);
}
