package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "student_curriculum_has_teacher_and_discipline")
@Entity
public class StudentCurriculumHasTeacherAndDiscipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Discipline must not be null")
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @NotNull(message = "Teacher must not be null")
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @NotNull(message = "Student curriculum must not be null")
    @ManyToOne
    @JoinColumn(name = "student_curriculum_id")
    private StudentCurriculum studentCurriculum;

}
