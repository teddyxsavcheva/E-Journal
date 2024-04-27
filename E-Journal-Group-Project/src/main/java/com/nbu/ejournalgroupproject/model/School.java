package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "school")
@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "school_type_id")
    private SchoolType schoolType;

    @OneToMany(mappedBy = "school")
    private List<Teacher> teachers;

    // TO-DO: To fix how this looks like in the database
    @OneToOne(mappedBy = "school")
    private Headmaster headmaster;

    @OneToMany(mappedBy = "school")
    private List<SchoolClass> schoolClasses;
}
