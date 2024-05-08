package com.nbu.ejournalgroupproject.model;
//moe
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nbu.ejournalgroupproject.enums.SchoolTypeEnum;
import jakarta.persistence.*;
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

    @Column(name = "school_type_enum")
    @Enumerated(EnumType.STRING)
    private SchoolTypeEnum schoolTypeEnum;

    @JsonIgnore
    @OneToMany(mappedBy = "schoolType")
    private List<School> schools;


}
