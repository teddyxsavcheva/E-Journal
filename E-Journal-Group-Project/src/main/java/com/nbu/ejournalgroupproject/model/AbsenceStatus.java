package com.nbu.ejournalgroupproject.model;

import com.nbu.ejournalgroupproject.enums.AbsenceStatusEnum;
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
@Table(name = "absence_status")
public class AbsenceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Absence status enum cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "absence_status_enum")
    private AbsenceStatusEnum absenceStatusEnum;

    @OneToMany(mappedBy = "absenceStatus")
    private List<Absence> absences;

}