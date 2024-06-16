package com.nbu.ejournalgroupproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO{

    private Long id;
    private String name;
    private String email;

    private Long schoolId;

    private List<Long> teacherQualificationIds;

}
