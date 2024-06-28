package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.StudentDTO;
import com.nbu.ejournalgroupproject.mappers.StudentMapper;
import com.nbu.ejournalgroupproject.model.Caregiver;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.repository.CaregiverRepository;
import com.nbu.ejournalgroupproject.repository.StudentRepository;
import com.nbu.ejournalgroupproject.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CaregiverRepository caregiverRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
        return studentMapper.toDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO createStudent(@Valid StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);
        Student createdStudent = studentRepository.save(student);
        return studentMapper.toDTO(createdStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDTO updateStudent(Long id, @Valid StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));

        Student updatedStudent = studentMapper.toEntity(studentDTO);
        updatedStudent.setId(existingStudent.getId());

        Student savedStudent = studentRepository.save(updatedStudent);
        return studentMapper.toDTO(savedStudent);
    }

    @Override
    public StudentDTO addCaregiverToStudent(Long studentId, Long caregiverId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + studentId));

        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(() -> new EntityNotFoundException("Caregiver not found with id " + caregiverId));

        student.getCaregivers().add(caregiver);
        Student updatedStudent = studentRepository.save(student);

        return studentMapper.toDTO(updatedStudent);
    }

    @Override
    public StudentDTO removeCaregiverFromStudent(Long studentId, Long caregiverId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + studentId));

        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(() -> new EntityNotFoundException("Caregiver not found with id " + caregiverId));

        student.getCaregivers().remove(caregiver);
        Student updatedStudent = studentRepository.save(student);

        return studentMapper.toDTO(updatedStudent);
    }

    @Override
    public List<StudentDTO> getStudentsFromClass(Long id) {
        List<Student> students = studentRepository.getStudentsBySchoolClassId(id);
        return students
                .stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }
}