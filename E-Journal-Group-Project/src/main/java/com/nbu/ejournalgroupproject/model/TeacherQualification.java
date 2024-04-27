package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.enums.TeacherQualificationEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @Column(name = "teacher_qualification_enum")
    private TeacherQualificationEnum qualificationEnum;

    @ManyToMany
    @JoinTable(name="teachers_have_teacher_qualifications",
            joinColumns=@JoinColumn(name="teacher_qualification_id"),
            inverseJoinColumns=@JoinColumn(name="teacher_id")
    )
    private List<Teacher> teachers;

    @ManyToMany
    @JoinTable(name="disciplines_have_teacher_qualifications",
            joinColumns=@JoinColumn(name="teacher_qualification_id"),
            inverseJoinColumns=@JoinColumn(name="discipline_id")
    )
    private List<Discipline> disciplines;

}
