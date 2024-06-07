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
    private Long teacherId;
    private Long curriculumId;

}
