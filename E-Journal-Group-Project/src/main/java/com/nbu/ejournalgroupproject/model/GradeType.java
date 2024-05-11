package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.enums.GradeTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "grade_type")
@Entity
public class GradeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "grade_type_enum")
    @Enumerated(EnumType.STRING)
    private GradeTypeEnum gradeTypeEnum;

    @OneToMany(mappedBy = "gradeType")
    private List<Grade> grades;

}
