package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.GradeDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeService {
    List<GradeDTO> getAllGrades();
    GradeDTO getGradeById(Long id);
    GradeDTO createGrade(GradeDTO gradeDTO);
    void deleteGrade(Long id);
    GradeDTO updateGrade(Long id, GradeDTO gradeDTO);
    List<String> getGradesByStudentAndDiscipline(Long studentId, Long disciplineId);
    List<String> findAvgGradeForSchool(Long headmasterId);
    List<String> findAvgGradeForDiscipline(Long headmasterId);
    List<String> findAvgGradeForTeacherByDiscipline(Long headmasterId);
    List<GradeDTO> getGradeObjectsByStudentAndDiscipline(Long studentId, Long disciplineId);
}