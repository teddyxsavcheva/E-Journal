package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.CaregiverDTO;
import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.model.Caregiver;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.model.user.User;
import com.nbu.ejournalgroupproject.repository.StudentRepository;
import com.nbu.ejournalgroupproject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CaregiverMapper {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public CaregiverDTO toDTO(Caregiver caregiver) {
        CaregiverDTO caregiverDTO = new CaregiverDTO();
        caregiverDTO.setId(caregiver.getId());
        caregiverDTO.setName(caregiver.getName());
        caregiverDTO.setEmail(caregiver.getEmail());
        Set<Long> studentIds = caregiver.getStudents().stream()
                .map(Student::getId)
                .collect(Collectors.toSet());
        caregiverDTO.setStudentIds(studentIds);

        // Checking if the user exists
       caregiverDTO.setUserId(getUserId(caregiver));

        return caregiverDTO;
    }

    public Caregiver toEntity(CaregiverDTO caregiverDTO) {
        Caregiver caregiver = new Caregiver();
        caregiver.setId(caregiverDTO.getId());
        caregiver.setName(caregiverDTO.getName());
        caregiver.setEmail(caregiverDTO.getEmail());

        if (caregiverDTO.getStudentIds() == null) {
            caregiver.setStudents(new HashSet<>());
        } else {
            Set<Student> students = caregiverDTO.getStudentIds().stream()
                    .map(id -> studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id)))
                    .collect(Collectors.toSet());
            caregiver.setStudents(new HashSet<>(students));
        }

        // Checking if the user exists
        caregiver.setUser(getUser(caregiverDTO));

        return caregiver;
    }

    public Long getUserId(Caregiver caregiver) {
        return caregiver.getUser() != null ? caregiver.getUser().getId() : null;
    }

    public User getUser(CaregiverDTO caregiverDTO) {
        if (caregiverDTO.getUserId() != null) {
            return userRepository.findById(caregiverDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("No User found with id " + caregiverDTO.getUserId()));
        } else {
            return null;
        }
    }
}