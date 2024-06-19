package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "discipline")
@Entity
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Discipline type must not be null")
    @ManyToOne
    @JoinColumn(name = "discipline_type_id")
    private DisciplineType disciplineType;

    @OneToMany(mappedBy = "discipline")
    private Set<StudentCurriculumHasTeacherAndDiscipline> curriculumHasTeacherAndDisciplineList;

    @OneToMany(mappedBy = "discipline")
    private List<Grade> grades;

    @OneToMany(mappedBy = "discipline")
    private List<Absence> absences;

    @ManyToMany
    @JoinTable(name="disciplines_have_teacher_qualifications",
            joinColumns=@JoinColumn(name="discipline_id"),
            inverseJoinColumns=@JoinColumn(name="teacher_qualification_id")
    )
    private Set<TeacherQualification> teacherQualifications;

}
