package com.nbu.ejournalgroupproject.dto;

import com.nbu.ejournalgroupproject.model.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CaregiverDTO {
    private Long id;
    private String name;
    private String email;
    private List<Student> students;
}
