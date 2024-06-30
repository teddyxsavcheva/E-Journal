package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;

import java.util.List;


public interface TeacherService {
    List<TeacherDTO> getTeachersFromSchool(Long SchoolId);

    List<TeacherDTO> getTeachers();

    TeacherDTO getTeacherById(Long teacherId);

    TeacherDTO createTeacher(TeacherDTO teacherDTO);

    TeacherDTO updateTeacher(Long teacherId, TeacherDTO teacherDTO);

    void deleteTeacher(Long teacherId);

    void validateTeacherDTO(TeacherDTO teacherDTO);

    TeacherDTO addQualificationToTeacher(Long teacherID, Long qualificationId);

    TeacherDTO deleteQualificationFromTeacher(Long teacherID, Long qualificationId);

    List<TeacherQualificationDto> getQualificationsByTeacherId(Long teacherId);


}
