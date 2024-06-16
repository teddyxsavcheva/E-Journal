package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.model.AbsenceStatus;
import com.nbu.ejournalgroupproject.model.AbsenceType;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AbsenceDTO {
    private Long id;
    private LocalDate dateOfIssue;
    private Student student;
    private Discipline discipline;
    private AbsenceType absenceType;
    private AbsenceStatus absenceStatus;
}
