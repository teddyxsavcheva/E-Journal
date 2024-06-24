package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.GradeDTO;
import java.util.List;

public interface GradeService {
    List<GradeDTO> getAllGrades();
    GradeDTO getGradeById(Long id);
    GradeDTO createGrade(GradeDTO gradeDTO);
    void deleteGrade(Long id);
    GradeDTO updateGrade(Long id, GradeDTO gradeDTO);
}
