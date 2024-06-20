package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.CaregiverDTO;
import com.nbu.ejournalgroupproject.model.Caregiver;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CaregiverMapper {

    private final StudentRepository studentRepository;

    public CaregiverDTO toDTO(Caregiver caregiver) {
        CaregiverDTO caregiverDTO = new CaregiverDTO();
        caregiverDTO.setId(caregiver.getId());
        caregiverDTO.setName(caregiver.getName());
        caregiverDTO.setEmail(caregiver.getEmail());
        List<Long> studentIds = caregiver.getStudents().stream()
                .map(Student::getId)
                .collect(Collectors.toList());
        caregiverDTO.setStudentIds(studentIds);
        return caregiverDTO;
    }

    public Caregiver toEntity(CaregiverDTO caregiverDTO) {
        Caregiver caregiver = new Caregiver();
        caregiver.setId(caregiverDTO.getId());
        caregiver.setName(caregiverDTO.getName());
        caregiver.setEmail(caregiverDTO.getEmail());

        List<Student> students = caregiverDTO.getStudentIds().stream()
                .map(id -> studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id)))
                .collect(Collectors.toList());
        caregiver.setStudents(new HashSet<>(students));

        return caregiver;
    }
}