package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.SchoolClass;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class SchoolClassMapper {
    private final SchoolRepository schoolRepository;

    public SchoolClassDTO mapEntityToDto(SchoolClass schoolClass){
        SchoolClassDTO schoolClassDTO = new SchoolClassDTO();
        schoolClassDTO.setId(schoolClass.getId());
        schoolClassDTO.setName(schoolClass.getName());
        schoolClassDTO.setYear(schoolClass.getYear());
        schoolClassDTO.setSchoolId(getSchoolId(schoolClass));

        return schoolClassDTO;
    }


    public SchoolClass mapDtoToEntity(SchoolClassDTO schoolClassDTO) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(schoolClassDTO.getId());
        schoolClass.setName(schoolClassDTO.getName());
        schoolClass.setYear(schoolClassDTO.getYear());
        schoolClass.setSchool(getSchool(schoolClassDTO));
        return schoolClass;
    }


    private Long getSchoolId(SchoolClass schoolClass) {
        if (schoolClass.getSchool() != null) {
            return schoolClass.getSchool().getId();
        }
        else throw new IllegalArgumentException("School cannot be null");
    }

    private School getSchool(SchoolClassDTO schoolClassDTO) {
        if (schoolClassDTO.getSchoolId() != null) {
            return schoolRepository.findById(schoolClassDTO.getSchoolId())
                    .orElseThrow(() -> new EntityNotFoundException("School not found"));
        }
        else throw new IllegalArgumentException("School cannot be null");
    }
}

