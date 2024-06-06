package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherQualificationDto {

    private Long id;

    private TeacherQualificationEnum qualificationEnum;

}
