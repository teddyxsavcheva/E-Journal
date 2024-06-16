package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.AbsenceTypeEnum;
import com.nbu.ejournalgroupproject.model.Absence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AbsenceTypeDTO {
    private Long id;
    private AbsenceTypeEnum absenceTypeEnum;
    private List<Absence> absences;
}
