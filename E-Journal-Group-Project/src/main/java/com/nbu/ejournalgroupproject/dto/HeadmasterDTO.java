package com.nbu.ejournalgroupproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadmasterDTO {
    private Long id;
    private String name;
    private String email;

    private Long schoolId;
}
