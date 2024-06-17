package com.nbu.ejournalgroupproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCurriculumDTO {
    private Long id;
    private int semester;
    private int year;
    private Long schoolClassId;
}
