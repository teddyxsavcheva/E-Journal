package com.nbu.ejournalgroupproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.StudentCurriculumHasTeacherAndDiscipline;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {

    private Long id;
    private String name;
    private String email;

    private Long schoolId;
    private List<TeacherQualification> teacherQualifications;

//    private List<StudentCurriculumHasTeacherAndDisciplineDTO> studentCurriculumHasTeacherAndDisciplines;

}
