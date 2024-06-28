package com.nbu.ejournalgroupproject.service;

import com.nbu.ejournalgroupproject.dto.CaregiverDTO;

import java.util.List;

public interface CaregiverService {
    List<CaregiverDTO> getAllCaregivers();
    CaregiverDTO getCaregiverById(Long id);
    CaregiverDTO createCaregiver(CaregiverDTO caregiverDTO);
    void deleteCaregiver(Long id);
    CaregiverDTO updateCaregiver(Long id, CaregiverDTO caregiverDTO);
    CaregiverDTO addStudentToCaregiver(Long caregiverId, Long studentId);
    CaregiverDTO removeStudentFromCaregiver(Long caregiverId, Long studentId);

    List<CaregiverDTO> getCaregiversFromStudentId(Long id);
}
