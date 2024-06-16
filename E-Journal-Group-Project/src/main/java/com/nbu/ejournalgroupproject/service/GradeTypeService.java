package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.GradeTypeDTO;

import java.util.List;

public interface GradeTypeService {
    List<GradeTypeDTO> getAllGradeTypes();
    GradeTypeDTO getGradeTypeById(Long id);
    GradeTypeDTO createGradeType(GradeTypeDTO gradeTypeDTO);
    void deleteGradeType(Long id);
    GradeTypeDTO updateGradeType(Long id, GradeTypeDTO gradeTypeDTO);
}
