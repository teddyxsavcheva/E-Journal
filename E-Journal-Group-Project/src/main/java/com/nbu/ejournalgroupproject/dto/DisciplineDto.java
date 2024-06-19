package com.nbu.ejournalgroupproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineDto {

    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @NotNull(message = "Discipline type must not be null")
    private Long disciplineTypeId;

    private Set<Long> teacherQualificationIds;

}
