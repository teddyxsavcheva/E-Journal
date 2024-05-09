package com.nbu.ejournalgroupproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class StudentDTO {
    private Long id;
    private String name;
    private int numberInClass;
    private Long schoolClassId;
    private List<Long> gradeIds;
    private List<Long> absenceIds;
    private List<Long> caregiverIds;
}
