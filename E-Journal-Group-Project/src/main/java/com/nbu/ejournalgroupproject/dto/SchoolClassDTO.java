package com.nbu.ejournalgroupproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClassDTO {
    private Long id;

    @NotBlank(message = "Address must not be blank")
    @Size(min = 2, max = 100, message = "Address must be between 2 and 100 characters")
    private String name;

    @Min(value = 2000, message = "Year must be greater than or equal to 2000")
    private int year;

    @NotNull(message = "School ID cannot be null")
    private Long schoolId;
}


