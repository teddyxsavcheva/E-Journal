package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.StudentCurriculumDTO;

import java.util.List;

public interface StudentCurriculumService {
    List<StudentCurriculumDTO> getStudentCurriculums();

    StudentCurriculumDTO getStudentCurriculum(Long id);

    StudentCurriculumDTO createStudentCurriculum(StudentCurriculumDTO studentCurriculumDTO);

    StudentCurriculumDTO updateStudentCurriculum(Long id, StudentCurriculumDTO studentCurriculumDTO);

    void deleteStudentCurriculum(Long id);

    void validateStudentCurriculumDTO(StudentCurriculumDTO studentCurriculumDTO);

    StudentCurriculumDTO getStudentCurriculumByClassId(long id);
}
