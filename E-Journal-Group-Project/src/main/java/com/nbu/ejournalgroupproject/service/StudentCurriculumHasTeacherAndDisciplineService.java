package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.StudentCurriculumHasTeacherAndDisciplineDto;

import java.util.List;

public interface StudentCurriculumHasTeacherAndDisciplineService {

    List<StudentCurriculumHasTeacherAndDisciplineDto> getAllCurriculumHasTeacherAndDiscipline();

    StudentCurriculumHasTeacherAndDisciplineDto getCurriculumHasTeacherAndDisciplineById(Long id);

    StudentCurriculumHasTeacherAndDisciplineDto createCurriculumHasTeacherAndDiscipline(StudentCurriculumHasTeacherAndDisciplineDto dto);

    StudentCurriculumHasTeacherAndDisciplineDto updateCurriculumHasTeacherAndDiscipline(StudentCurriculumHasTeacherAndDisciplineDto dto, Long id);

    void deleteCurriculumHasTeacherAndDiscipline(Long id);

    void validateCurriculumHasTeacherAndDisciplineDto(StudentCurriculumHasTeacherAndDisciplineDto dto);

}
