package com.nbu.ejournalgroupproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDTO {

    private Long id;
    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 200 characters")
    private String name;

    @NotBlank(message = "Address must not be blank")
    @Size(min = 2, max = 200, message = "Address must be between 2 and 200 characters")
//    @Pattern(regexp = "^[a-zA-Z0-9 .,-]*$", message = "Address must contain only alphanumeric characters, spaces, dots, commas, and dashes")
    private String address;

    @NotNull(message = "School Type ID cannot be null")
    private Long schoolTypeId;
}


