package com.nbu.ejournalgroupproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DisciplineDTO {

    //TODO: Think about validations.
    private Long id;
    private String name;
    private Long disciplineTypeId;

}
