package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "discipline")
@Entity
public class Discipline {
    //TODO: Think about validations.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "discipline_type_id")
    private DisciplineType disciplineType;

    @OneToMany(mappedBy = "discipline")
    private List<StudentCurriculumHasTeacherAndDiscipline> curriculumHasTeacherAndDisciplineList;

    @OneToMany(mappedBy = "discipline")
    private List<Grade> grades;

    @OneToMany(mappedBy = "discipline")
    private List<Absence> absences;

    @ManyToMany
    @JoinTable(name="disciplines_have_teacher_qualifications",
            joinColumns=@JoinColumn(name="discipline_id"),
            inverseJoinColumns=@JoinColumn(name="teacher_qualification_id")
    )
    private List<TeacherQualification> teacherQualifications;

}
