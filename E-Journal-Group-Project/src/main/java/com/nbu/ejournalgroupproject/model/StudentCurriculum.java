package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
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

    @Column(name = "semester")
    private int semester;

    @Column(name = "year")
    private int year;

    @OneToOne
    private SchoolClass schoolClass;

    @OneToMany(mappedBy = "studentCurriculum")
    private List<StudentCurriculumHasTeacherAndDiscipline> curriculumHasTeacherAndDisciplineList;

}
