package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolTypeDTO {
    private Long id;

    @NotNull(message = "School Type must not be null")
    private SchoolTypeEnum schoolType;
}
