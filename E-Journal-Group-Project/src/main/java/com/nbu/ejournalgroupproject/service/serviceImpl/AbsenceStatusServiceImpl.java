package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.AbsenceStatusDTO;
import com.nbu.ejournalgroupproject.mappers.AbsenceStatusMapper;
import com.nbu.ejournalgroupproject.model.AbsenceStatus;
import com.nbu.ejournalgroupproject.repository.AbsenceStatusRepository;
import com.nbu.ejournalgroupproject.service.AbsenceStatusService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AbsenceStatusServiceImpl implements AbsenceStatusService {

    private final AbsenceStatusRepository absenceStatusRepository;
    private final AbsenceStatusMapper absenceStatusMapper;

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','HEADMASTER', 'TEACHER', 'STUDENT', 'CAREGIVER')")
    @Override
    public AbsenceStatusDTO getAbsenceStatusById(Long id) {
        AbsenceStatus absenceStatus = absenceStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AbsenceStatus not found with id " + id));
        return absenceStatusMapper.toDTO(absenceStatus);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @Override
    public List<AbsenceStatusDTO> getAllAbsenceStatuses() {
        List<AbsenceStatus> absenceStatuses = absenceStatusRepository.findAll();
        return absenceStatuses.stream()
                .map(absenceStatusMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public AbsenceStatusDTO createAbsenceStatus(@Valid AbsenceStatusDTO absenceStatusDTO) {
        AbsenceStatus absenceStatus = absenceStatusMapper.toEntity(absenceStatusDTO);
        AbsenceStatus createdAbsenceStatus = absenceStatusRepository.save(absenceStatus);
        return absenceStatusMapper.toDTO(createdAbsenceStatus);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public void deleteAbsenceStatus(Long id) {
        absenceStatusRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public AbsenceStatusDTO updateAbsenceStatus(Long id, @Valid AbsenceStatusDTO absenceStatusDTO) {
        AbsenceStatus existingAbsenceStatus = absenceStatusRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AbsenceStatus not found with id " + id));

        AbsenceStatus updatedAbsenceStatus = absenceStatusMapper.toEntity(absenceStatusDTO);
        updatedAbsenceStatus.setId(existingAbsenceStatus.getId());

        AbsenceStatus savedAbsenceStatus = absenceStatusRepository.save(updatedAbsenceStatus);
        return absenceStatusMapper.toDTO(savedAbsenceStatus);
    }
}