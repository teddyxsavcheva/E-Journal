package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "teacher")
@Entity
// TODO: Add user
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToMany
    @JoinTable(name="teachers_have_teacher_qualifications",
            joinColumns=@JoinColumn(name="teacher_id"),
            inverseJoinColumns=@JoinColumn(name="teacher_qualification_id")
    )
    private Set<TeacherQualification> teacherQualifications;

    @OneToMany(mappedBy = "teacher")
    private List<StudentCurriculumHasTeacherAndDiscipline> curriculumHasTeacherAndDisciplineList;

}
