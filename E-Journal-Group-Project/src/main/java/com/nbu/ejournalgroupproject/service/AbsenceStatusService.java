package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.AbsenceStatusDTO;
import java.util.List;

public interface AbsenceStatusService {
    List<AbsenceStatusDTO> getAllAbsenceStatuses();
    AbsenceStatusDTO getAbsenceStatusById(Long id);
    AbsenceStatusDTO createAbsenceStatus(AbsenceStatusDTO absenceStatusDTO);
    void deleteAbsenceStatus(Long id);
    AbsenceStatusDTO updateAbsenceStatus(Long id, AbsenceStatusDTO absenceStatusDTO);
}
