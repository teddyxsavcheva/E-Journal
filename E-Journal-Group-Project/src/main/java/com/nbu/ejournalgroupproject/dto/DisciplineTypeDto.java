package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
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

    private DisciplineTypeEnum disciplineType;

}
