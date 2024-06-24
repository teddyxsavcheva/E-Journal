package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineTypeDto {

    private Long id;

    @NotNull(message = "Discipline type must not be null")
    private DisciplineTypeEnum disciplineType;

}
