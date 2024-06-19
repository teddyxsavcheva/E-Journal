package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.enums.DisciplineTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Discipline type enum must not be null")
    @Column(name = "discipline_type_enum")
    @Enumerated(EnumType.STRING)
    private DisciplineTypeEnum disciplineTypeEnum;

    @NotNull(message = "Disciplines list must not be null")
    @OneToMany(mappedBy = "disciplineType")
    private List<Discipline> discipline;

}
