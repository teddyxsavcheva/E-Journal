package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DisciplineTypeDTO {

    //TODO: Think about validations.
    private Long id;
    private DisciplineTypeEnum disciplineType;

}
