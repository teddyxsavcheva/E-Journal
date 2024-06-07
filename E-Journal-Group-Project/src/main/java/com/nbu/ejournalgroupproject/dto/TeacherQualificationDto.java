package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
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

    private TeacherQualificationEnum qualificationEnum;

    // TODO: Many-to-Many logic
    //private Set<Long> disciplineIds;

}
