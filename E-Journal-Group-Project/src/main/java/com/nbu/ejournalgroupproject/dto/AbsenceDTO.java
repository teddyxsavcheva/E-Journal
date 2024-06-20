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
public class AbsenceDTO {
    private Long id;

    @NotNull(message = "Date of issue cannot be null")
    @PastOrPresent(message = "Date of issue cannot be in the future")
    private LocalDate dateOfIssue;

    @NotNull(message = "Student ID cannot be null")
    private Long studentId;

    @NotNull(message = "Discipline ID cannot be null")
    private Long disciplineId;

    @NotNull(message = "Absence Type ID cannot be null")
    private Long absenceTypeId;

    @NotNull(message = "Absence Status ID cannot be null")
    private Long absenceStatusId;
}