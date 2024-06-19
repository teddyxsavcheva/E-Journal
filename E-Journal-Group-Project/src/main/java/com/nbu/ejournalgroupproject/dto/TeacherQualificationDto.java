package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherQualificationDto {

    private Long id;

    @NotNull(message = "Teacher Qualification Enum cannot be null")
    private TeacherQualificationEnum qualificationEnum;

    private Set<Long> disciplineIds;

    private Set<Long> teacherIds;

}
