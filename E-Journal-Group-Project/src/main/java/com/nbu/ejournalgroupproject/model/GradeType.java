package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.enums.GradeTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "grade_type")
public class GradeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Grade type enum cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "grade_type_enum")
    private GradeTypeEnum gradeTypeEnum;

    @OneToMany(mappedBy = "gradeType")
    private List<Grade> grades;
}