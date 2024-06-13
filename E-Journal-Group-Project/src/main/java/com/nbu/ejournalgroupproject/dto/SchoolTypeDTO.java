package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolTypeDTO {
    private Long id;

    private SchoolTypeEnum schoolType;
}
