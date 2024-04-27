package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "school_class")
@Entity
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @OneToOne(mappedBy = "schoolClass")
    private StudentCurriculum studentCurriculums;

    @OneToMany(mappedBy = "schoolClass")
    private List<Student> students;

}
