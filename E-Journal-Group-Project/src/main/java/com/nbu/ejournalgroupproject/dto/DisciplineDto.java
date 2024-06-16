package com.nbu.ejournalgroupproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineDto {

    private Long id;

    private String name;

    private Long disciplineTypeId;

    private List<Long> teacherQualificationIds;

}
