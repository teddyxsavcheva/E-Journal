package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.CaregiverDTO;
import com.nbu.ejournalgroupproject.mappers.CaregiverMapper;
import com.nbu.ejournalgroupproject.model.Caregiver;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.repository.CaregiverRepository;
import com.nbu.ejournalgroupproject.repository.StudentRepository;
import com.nbu.ejournalgroupproject.service.CaregiverService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CaregiverServiceImpl implements CaregiverService {

    private final CaregiverRepository caregiverRepository;
    private final StudentRepository studentRepository;
    private final CaregiverMapper caregiverMapper;

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','HEADMASTER', 'CAREGIVER')")
    @Override
    public CaregiverDTO getCaregiverById(Long id) {
        Caregiver caregiver = caregiverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Caregiver not found with id " + id));
        return caregiverMapper.toDTO(caregiver);
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @Override
    public List<CaregiverDTO> getAllCaregivers() {
        List<Caregiver> caregivers = caregiverRepository.findAll();
        return caregivers.stream()
                .map(caregiverMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public CaregiverDTO createCaregiver(@Valid CaregiverDTO caregiverDTO) {
        Caregiver caregiver = caregiverMapper.toEntity(caregiverDTO);
        Caregiver createdCaregiver = caregiverRepository.save(caregiver);
        return caregiverMapper.toDTO(createdCaregiver);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public void deleteCaregiver(Long id) {
        caregiverRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public CaregiverDTO updateCaregiver(Long id, @Valid CaregiverDTO caregiverDTO) {
        Caregiver existingCaregiver = caregiverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Caregiver not found with id " + id));

        Caregiver updatedCaregiver = caregiverMapper.toEntity(caregiverDTO);
        updatedCaregiver.setId(existingCaregiver.getId());

        Caregiver savedCaregiver = caregiverRepository.save(updatedCaregiver);
        return caregiverMapper.toDTO(savedCaregiver);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public CaregiverDTO addStudentToCaregiver(Long caregiverId, Long studentId) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(() -> new EntityNotFoundException("Caregiver not found with id " + caregiverId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + studentId));

        if (!studentRepository.hasSingleCaregiver(studentId))
        {
            throw new IllegalArgumentException("Student have two caregivers");
        }

        caregiver.getStudents().add(student);
        Caregiver updatedCaregiver = caregiverRepository.save(caregiver);

        return caregiverMapper.toDTO(updatedCaregiver);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public CaregiverDTO removeStudentFromCaregiver(Long caregiverId, Long studentId) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(() -> new EntityNotFoundException("Caregiver not found with id " + caregiverId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + studentId));

        caregiver.getStudents().remove(student);
        Caregiver updatedCaregiver = caregiverRepository.save(caregiver);

        return caregiverMapper.toDTO(updatedCaregiver);
    }
}