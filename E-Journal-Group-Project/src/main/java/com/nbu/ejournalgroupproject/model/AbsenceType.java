package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.enums.AbsenceTypeEnum;
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
@Table(name = "absence_type")
public class AbsenceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Absence type enum cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "absence_type_enum")
    private AbsenceTypeEnum absenceTypeEnum;

    @OneToMany(mappedBy = "absenceType")
    private List<Absence> absences;
}