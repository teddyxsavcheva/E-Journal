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
        return absenceStatusDTO;
    }

    public AbsenceStatus toEntity(AbsenceStatusDTO absenceStatusDTO) {
        AbsenceStatus absenceStatus = new AbsenceStatus();
        absenceStatus.setId(absenceStatusDTO.getId());
        absenceStatus.setAbsenceStatusEnum(absenceStatusDTO.getAbsenceStatusEnum());
        return absenceStatus;
    }
}