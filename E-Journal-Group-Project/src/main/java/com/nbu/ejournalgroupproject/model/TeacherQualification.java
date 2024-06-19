package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "teacher_qualification")
@Entity
public class TeacherQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Teacher qualification enum must not be null")
    @Column(name = "teacher_qualification_enum")
    @Enumerated(EnumType.STRING)
    private TeacherQualificationEnum qualificationEnum;

    @ManyToMany
    @JoinTable(name="teachers_have_teacher_qualifications",
            joinColumns=@JoinColumn(name="teacher_qualification_id"),
            inverseJoinColumns=@JoinColumn(name="teacher_id")
    )
    private Set<Teacher> teachers;

    @ManyToMany
    @JoinTable(name="disciplines_have_teacher_qualifications",
            joinColumns=@JoinColumn(name="teacher_qualification_id"),
            inverseJoinColumns=@JoinColumn(name="discipline_id")
    )
    private Set<Discipline> disciplines;

}
