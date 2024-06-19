package com.nbu.ejournalgroupproject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCurriculumDTO {
    private Long id;

    @Min(value = 1, message = "Semester must be at least 1") //TODO [1,?]
    private int semester;

    @Min(value = 1, message = "Year must be at least 1")
    @Max(value = 12, message = "Year must be at most 12")
    private int year;

    @NotNull(message = "School class cannot be null")
    private Long schoolClassId;
}
