package com.nbu.ejournalgroupproject.model;


import com.nbu.ejournalgroupproject.enums.AbsenceStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "absence_status")
@Entity
public class AbsenceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "absence_status_enum")
    @Enumerated(EnumType.STRING)
    private AbsenceStatusEnum absenceStatusEnum;

    @OneToMany(mappedBy = "absenceStatus")
    private List<Absence> absences;

}
