package com.nbu.ejournalgroupproject.dto;

import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Discipline must not be null")
    private Long disciplineId;

    @NotNull(message = "Teacher must not be null")
    private Long teacherId;

    @NotNull(message = "Student curriculum must not be null")
    private Long curriculumId;

}
