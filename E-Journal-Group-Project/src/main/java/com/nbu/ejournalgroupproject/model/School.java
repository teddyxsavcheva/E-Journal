package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters")
    @Column(name = "name")
    private String name;


    @NotBlank(message = "Address must not be blank")
    @Size(min = 2, max = 200, message = "Address must be between 2 and 200 characters")
//    @Pattern(regexp = "^[a-zA-Z0-9 .,-]*$", message = "Address must contain only alphanumeric characters, spaces, dots, commas, and dashes")
    @Column(name = "address")
    private String address;

    @NotNull(message = "School Type ID cannot be null")
    @ManyToOne
    @JoinColumn(name = "school_type_id")
    private SchoolType schoolType;

    @OneToMany(mappedBy = "school")
    private List<Teacher> teachers;

    @OneToOne(mappedBy = "school")
    private Headmaster headmaster;

    @OneToMany(mappedBy = "school")
    private List<SchoolClass> schoolClasses;
}
