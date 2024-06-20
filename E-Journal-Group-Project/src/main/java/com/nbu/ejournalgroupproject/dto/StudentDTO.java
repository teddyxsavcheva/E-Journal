package com.nbu.ejournalgroupproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Min(value = 1, message = "Number in class must be at least 1")
    private int numberInClass;

    @NotNull(message = "School class ID cannot be null")
    private Long schoolClassId;

    @NotEmpty(message = "Grade IDs cannot be empty")
    private List<Long> gradeIds;

    @NotEmpty(message = "Absence IDs cannot be empty")
    private List<Long> absenceIds;

    @NotEmpty(message = "Caregiver IDs cannot be empty")
    private List<Long> caregiverIds;
}