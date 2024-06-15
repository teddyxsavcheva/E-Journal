package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.enums.AbsenceTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "absence_type")
@Entity
public class AbsenceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "absence_type_enum")
    @Enumerated(EnumType.STRING)
    private AbsenceTypeEnum absenceTypeEnum;

    @OneToMany(mappedBy = "absenceType")
    private List<Absence> absences;

}
