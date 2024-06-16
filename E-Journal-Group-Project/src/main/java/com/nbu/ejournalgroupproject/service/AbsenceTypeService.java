package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.AbsenceTypeDTO;
import java.util.List;

public interface AbsenceTypeService {
    List<AbsenceTypeDTO> getAllAbsenceTypes();
    AbsenceTypeDTO getAbsenceTypeById(Long id);
    AbsenceTypeDTO createAbsenceType(AbsenceTypeDTO absenceTypeDTO);
    void deleteAbsenceType(Long id);
    AbsenceTypeDTO updateAbsenceType(Long id, AbsenceTypeDTO absenceTypeDTO);
}
