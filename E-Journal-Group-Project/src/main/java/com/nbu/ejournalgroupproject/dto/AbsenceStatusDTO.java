package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.AbsenceStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AbsenceStatusDTO {
    private Long id;

    @NotNull(message = "Absence status enum cannot be null")
    private AbsenceStatusEnum absenceStatusEnum;
}
