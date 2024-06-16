package com.nbu.ejournalgroupproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClassDTO {
    private Long id;
    private String name;
    private int year;
    private Long schoolId;
    private Long studentCurriculumId;
    private List<Long> studentIds;
}
