package com.nbu.ejournalgroupproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentCurriculumHasTeacherAndDisciplineDto {

    private Long id;
    private Long disciplineId;
    // TODO: I will have to make a query in the teacher's service to check if it has the qualification for the certain discipline
    private Long teacherId;
    private Long curriculumId;

}
