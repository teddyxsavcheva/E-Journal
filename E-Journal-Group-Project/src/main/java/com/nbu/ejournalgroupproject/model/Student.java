package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @Min(value = 1, message = "Number in class must be at least 1")
    @Column(name = "number_in_class")
    private int numberInClass;

    @NotNull(message = "School class cannot be null")
    @ManyToOne
    @JoinColumn(name = "school_class_id")
    private SchoolClass schoolClass;

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;

    @OneToMany(mappedBy = "student")
    private List<Absence> absences;

    @ManyToMany
    @JoinTable(name = "student_caregiver",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "caregiver_id")
    )
    private Set<Caregiver> caregivers = new HashSet<>();
}