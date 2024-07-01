package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "student_curriculum")
@Entity
public class StudentCurriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Min(value = 1, message = "Semester must be at least 1") //TODO [1,?]
    @Column(name = "semester")
    private int semester;

    @Min(value = 1, message = "Year must be at least 1")
    @Max(value = 12, message = "Year must be at most 12")
    @Column(name = "years")
    private int year;

    @NotNull(message = "School class cannot be null")
    @OneToOne
    private SchoolClass schoolClass;

    @OneToMany(mappedBy = "studentCurriculum")
    private List<StudentCurriculumHasTeacherAndDiscipline> curriculumHasTeacherAndDisciplineList;

}

