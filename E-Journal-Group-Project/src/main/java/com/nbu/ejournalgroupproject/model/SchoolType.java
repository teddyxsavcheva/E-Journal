package com.nbu.ejournalgroupproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "school_type")
@Entity
public class SchoolType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "School Type must not be null")
    @Column(name = "school_type_enum")
    @Enumerated(EnumType.STRING) //unique proveri
    private SchoolTypeEnum schoolTypeEnum;

    @JsonIgnore
    @OneToMany(mappedBy = "schoolType")
    private List<School> schools;


}
