package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.model.StudentCurriculum;
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
    private SchoolDTO school;
    private StudentCurriculumDTO studentCurriculum;
//    private List<StudentDTO> students;
}
