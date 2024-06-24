package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.enums.GradeTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GradeTypeDTO {
    private Long id;

    @NotNull(message = "Grade type enum cannot be null")
    private GradeTypeEnum gradeTypeEnum;
}