package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.AbsenceTypeDTO;
import com.nbu.ejournalgroupproject.model.Absence;
import com.nbu.ejournalgroupproject.model.AbsenceType;
import com.nbu.ejournalgroupproject.repository.AbsenceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AbsenceTypeMapper {

    private AbsenceRepository absenceRepository;

    public AbsenceTypeDTO toDTO(AbsenceType absenceType) {
        AbsenceTypeDTO absenceTypeDTO = new AbsenceTypeDTO();
        absenceTypeDTO.setId(absenceType.getId());
        absenceTypeDTO.setAbsenceTypeEnum(absenceType.getAbsenceTypeEnum());
        return absenceTypeDTO;
    }

    public AbsenceType toEntity(AbsenceTypeDTO absenceTypeDTO) {
        AbsenceType absenceType = new AbsenceType();
        absenceType.setId(absenceTypeDTO.getId());
        absenceType.setAbsenceTypeEnum(absenceTypeDTO.getAbsenceTypeEnum());
        return absenceType;
    }
}