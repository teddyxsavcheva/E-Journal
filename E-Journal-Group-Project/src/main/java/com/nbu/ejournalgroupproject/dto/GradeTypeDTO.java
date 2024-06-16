package com.nbu.ejournalgroupproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GradeTypeDTO {
    private Long id;
    private String gradeTypeEnum;
    private List<Long> gradesIds;

}
