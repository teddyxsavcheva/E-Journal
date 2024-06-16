package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.AbsenceStatusEnum;
import com.nbu.ejournalgroupproject.model.Absence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AbsenceStatusDTO {
    private Long id;
    private AbsenceStatusEnum absenceStatusEnum;
    private List<Absence> absences;
}
