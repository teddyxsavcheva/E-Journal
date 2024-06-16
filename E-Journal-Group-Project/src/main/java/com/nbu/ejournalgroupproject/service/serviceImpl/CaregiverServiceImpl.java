package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.CaregiverDTO;
import com.nbu.ejournalgroupproject.model.Caregiver;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.repository.CaregiverRepository;
import com.nbu.ejournalgroupproject.repository.StudentRepository;
import com.nbu.ejournalgroupproject.service.CaregiverService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CaregiverServiceImpl implements CaregiverService {
    private final CaregiverRepository caregiverRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public CaregiverDTO getCaregiverById(Long id) {
        Caregiver caregiver = caregiverRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Caregiver not found with id " + id));
        return modelMapper.map(caregiver, CaregiverDTO.class);
    }

    @Override
    public List<CaregiverDTO> getAllCaregivers() {
        List<Caregiver> caregivers = caregiverRepository.findAll();
        return caregivers.stream()
                .map(caregiver -> modelMapper.map(caregiver, CaregiverDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CaregiverDTO createCaregiver(CaregiverDTO caregiverDTO) {
        Caregiver caregiver = modelMapper.map(caregiverDTO, Caregiver.class);
        Caregiver createdCaregiver = caregiverRepository.save(caregiver);
        return modelMapper.map(createdCaregiver, CaregiverDTO.class);
    }

    @Override
    public void deleteCaregiver(Long id) {
        caregiverRepository.deleteById(id);
    }

    @Override
    public CaregiverDTO updateCaregiver(Long id, CaregiverDTO caregiverDTO) {
        Caregiver caregiver = caregiverRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Caregiver not found with id " + id));
        modelMapper.map(caregiverDTO, caregiver);
        Caregiver updatedCaregiver = caregiverRepository.save(caregiver);
        return modelMapper.map(updatedCaregiver, CaregiverDTO.class);
    }

    @Override
    public CaregiverDTO addStudentToCaregiver(Long caregiverId, Long studentId) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(() -> new EntityNotFoundException("Caregiver not found with id " + caregiverId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + studentId));

        caregiver.getStudents().add(student);
        Caregiver updatedCaregiver = caregiverRepository.save(caregiver);

        return modelMapper.map(updatedCaregiver, CaregiverDTO.class);
    }

    @Override
    public CaregiverDTO removeStudentFromCaregiver(Long caregiverId, Long studentId) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(() -> new EntityNotFoundException("Caregiver not found with id " + caregiverId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + studentId));

        caregiver.getStudents().remove(student);
        Caregiver updatedCaregiver = caregiverRepository.save(caregiver);

        return modelMapper.map(updatedCaregiver, CaregiverDTO.class);
    }
}
