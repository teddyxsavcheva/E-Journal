package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "caregiver")
@Entity
public class Caregiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @ManyToMany
    @JoinTable(name="caregivers_have_students",
            joinColumns=@JoinColumn(name="caregiver_id"),
            inverseJoinColumns=@JoinColumn(name="student_id")
    )
    private List<Student> students;

}
