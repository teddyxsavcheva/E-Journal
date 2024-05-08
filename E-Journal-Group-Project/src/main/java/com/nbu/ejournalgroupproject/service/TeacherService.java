package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.TeacherDTO;

import java.util.List;


public interface TeacherService {
    void createTeacher(TeacherDTO teacherDTO);

    TeacherDTO getTeacher(Long teacherId);
    List<TeacherDTO> getTeachersFromSchool(Long SchoolId);
    List<TeacherDTO> getTeachers();

    void updateTeacher(Long teacherId, TeacherDTO teacherDTO);

    boolean deleteTeacher(Long teacherId);



}
