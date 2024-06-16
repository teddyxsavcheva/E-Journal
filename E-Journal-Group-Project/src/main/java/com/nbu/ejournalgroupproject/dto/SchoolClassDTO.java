package com.nbu.ejournalgroupproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClassDTO {
    private Long id;
    private String name;
    private int year;
    private Long schoolId;
//    private Long studentCurriculumId;
}
