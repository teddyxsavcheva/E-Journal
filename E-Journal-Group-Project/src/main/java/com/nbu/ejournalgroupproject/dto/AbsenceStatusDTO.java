package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.AbsenceStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AbsenceStatusDTO {
    private Long id;

    @NotNull(message = "Absence status enum cannot be null")
    private AbsenceStatusEnum absenceStatusEnum;

    @NotEmpty(message = "Absence IDs cannot be empty")
    private List<Long> absenceIds;
}
