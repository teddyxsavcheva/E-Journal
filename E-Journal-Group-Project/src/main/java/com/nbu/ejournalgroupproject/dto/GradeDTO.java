package com.nbu.ejournalgroupproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GradeDTO {
    private Long id;
    private Date dateOfIssue;
    private Long studentId;
    private Long disciplineId;
    private Long gradeTypeId;
}
