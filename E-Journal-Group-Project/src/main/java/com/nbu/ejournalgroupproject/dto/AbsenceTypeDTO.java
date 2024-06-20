package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.AbsenceTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AbsenceTypeDTO {
    private Long id;

    @NotNull(message = "Absence type enum cannot be null")
    private AbsenceTypeEnum absenceTypeEnum;

    @NotEmpty(message = "Absence IDs cannot be empty")
    private List<Long> absenceIds;
}