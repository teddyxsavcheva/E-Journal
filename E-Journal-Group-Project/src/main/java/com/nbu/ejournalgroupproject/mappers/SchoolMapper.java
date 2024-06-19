package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.SchoolDTO;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.SchoolType;
import com.nbu.ejournalgroupproject.repository.SchoolTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SchoolMapper {

    private final SchoolTypeRepository schoolTypeRepository;

    public SchoolDTO mapEntityToDto(School school) {
        SchoolDTO schoolDTO = new SchoolDTO();
        schoolDTO.setId(school.getId());
        schoolDTO.setName(school.getName());
        schoolDTO.setAddress(school.getAddress());
        schoolDTO.setSchoolTypeId(getSchoolTypeId(school));

        return schoolDTO;
    }

    public School mapDtoToEntity(SchoolDTO schoolDTO) {
        School school = new School();
        school.setId(schoolDTO.getId());
        school.setName(schoolDTO.getName());
        school.setAddress(schoolDTO.getAddress());
        school.setSchoolType(getSchoolType(schoolDTO));
        return school;
    }

    public SchoolType getSchoolType(SchoolDTO schoolDTO) {
        if (schoolDTO.getSchoolTypeId() != null) {
            return schoolTypeRepository.findById(schoolDTO.getSchoolTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("School type with id " + schoolDTO.getSchoolTypeId() + " not found"));
        }
        else throw new EntityNotFoundException("No SchoolType found");
    }

    public Long getSchoolTypeId(School school) {
        return school.getSchoolType().getId();
    }
}
