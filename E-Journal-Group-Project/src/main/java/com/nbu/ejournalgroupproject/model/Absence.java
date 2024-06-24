package com.nbu.ejournalgroupproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "absence")
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date of issue cannot be null")
    @PastOrPresent(message = "Date of issue cannot be in the future")
    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @NotNull(message = "Student cannot be null")
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @NotNull(message = "Discipline cannot be null")
    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @NotNull(message = "Absence Type cannot be null")
    @ManyToOne
    @JoinColumn(name = "absence_type_id")
    private AbsenceType absenceType;

    @NotNull(message = "Absence Status cannot be null")
    @ManyToOne
    @JoinColumn(name = "absence_status_id")
    private AbsenceStatus absenceStatus;
}