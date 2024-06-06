package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;

import java.util.List;

public interface TeacherQualificationService {

    List<TeacherQualificationDto> getAllTeacherQualifications();

    TeacherQualificationDto getTeacherQualificationById(Long id);

    TeacherQualificationDto createTeacherQualification(TeacherQualificationDto dto);

    TeacherQualificationDto updateTeacherQualification(TeacherQualificationDto dto, Long id);

    void deleteTeacherQualification(Long id);

    void validateTeacherQualificationDto(TeacherQualificationDto dto);

}
