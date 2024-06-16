package com.nbu.ejournalgroupproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDTO {

    private Long id;
    private String name;
    private String address;
    private Long schoolTypeId;
}


