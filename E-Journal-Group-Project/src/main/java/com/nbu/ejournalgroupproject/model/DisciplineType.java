package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "discipline_type")
@Entity
public class DisciplineType {
    //TODO: Think about validations.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "discipline_type_enum")
    @Enumerated(EnumType.STRING)
    private DisciplineTypeEnum disciplineTypeEnum;

    @OneToMany(mappedBy = "disciplineType")
    private List<Discipline> discipline;

}
