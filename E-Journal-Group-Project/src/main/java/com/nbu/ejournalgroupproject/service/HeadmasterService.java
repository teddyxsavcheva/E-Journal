package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.HeadmasterDTO;

import java.util.List;

public interface HeadmasterService {
    List<HeadmasterDTO> getHeadmasters();

    HeadmasterDTO getHeadmaster(Long id);

    HeadmasterDTO getHeadmasterBySchoolID(Long id);

    void createHeadmaster(HeadmasterDTO headmasterDTO);

    boolean deleteHeadmaster(Long id);

    void updateHeadmaster(Long id, HeadmasterDTO headmasterDTO);

}
