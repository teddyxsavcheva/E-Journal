package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.AbsenceStatusDTO;
import com.nbu.ejournalgroupproject.model.Absence;
import com.nbu.ejournalgroupproject.model.AbsenceStatus;
import com.nbu.ejournalgroupproject.repository.AbsenceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AbsenceStatusMapper {

    private AbsenceRepository absenceRepository;

    public AbsenceStatusDTO toDTO(AbsenceStatus absenceStatus) {
        AbsenceStatusDTO absenceStatusDTO = new AbsenceStatusDTO();
        absenceStatusDTO.setId(absenceStatus.getId());
        absenceStatusDTO.setAbsenceStatusEnum(absenceStatus.getAbsenceStatusEnum());
        List<Long> absenceIds = absenceStatus.getAbsences().stream()
                .map(Absence::getId)
                .collect(Collectors.toList());
        absenceStatusDTO.setAbsenceIds(absenceIds);
        return absenceStatusDTO;
    }

    public AbsenceStatus toEntity(AbsenceStatusDTO absenceStatusDTO) {
        AbsenceStatus absenceStatus = new AbsenceStatus();
        absenceStatus.setId(absenceStatusDTO.getId());
        absenceStatus.setAbsenceStatusEnum(absenceStatusDTO.getAbsenceStatusEnum());

        List<Absence> absences = absenceStatusDTO.getAbsenceIds().stream()
                .map(id -> absenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Absence not found with id " + id)))
                .collect(Collectors.toList());
        absenceStatus.setAbsences(absences);

        return absenceStatus;
    }
}