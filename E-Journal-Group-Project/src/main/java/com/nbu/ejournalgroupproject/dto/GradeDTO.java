package com.nbu.ejournalgroupproject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class GradeDTO {
    private Long id;

    private LocalDate dateOfIssue = LocalDate.now();

    @NotNull(message = "Student ID cannot be null")
    private Long studentId;

    @NotNull(message = "Discipline ID cannot be null")
    private Long disciplineId;

    @NotNull(message = "Grade Type ID cannot be null")
    private Long gradeTypeId;
}