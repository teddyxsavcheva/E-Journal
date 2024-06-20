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
        List<Long> absenceIds = absenceType.getAbsences().stream()
                .map(Absence::getId)
                .collect(Collectors.toList());
        absenceTypeDTO.setAbsenceIds(absenceIds);
        return absenceTypeDTO;
    }

    public AbsenceType toEntity(AbsenceTypeDTO absenceTypeDTO) {
        AbsenceType absenceType = new AbsenceType();
        absenceType.setId(absenceTypeDTO.getId());
        absenceType.setAbsenceTypeEnum(absenceTypeDTO.getAbsenceTypeEnum());

        List<Absence> absences = absenceTypeDTO.getAbsenceIds().stream()
                .map(id -> absenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Absence not found with id " + id)))
                .collect(Collectors.toList());
        absenceType.setAbsences(absences);

        return absenceType;
    }
}