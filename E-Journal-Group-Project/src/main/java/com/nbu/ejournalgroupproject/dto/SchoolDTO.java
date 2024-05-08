package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.model.Headmaster;
import com.nbu.ejournalgroupproject.model.SchoolType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDTO {

    private Long id;
    private String name;
    private String address;
    private SchoolType schoolType;
    private List<TeacherDTO> teachers;
    private Long headmasterId; //TODO HEADMASTER OR ID
    private List<SchoolClassDTO> schoolClasses;
}

//TODO HOW TO CREATE NEW SCHOOL - only name and address?

