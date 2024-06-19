package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.mappers.TeacherMapper;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import com.nbu.ejournalgroupproject.repository.TeacherQualificationRepository;
import com.nbu.ejournalgroupproject.repository.TeacherRepository;
import com.nbu.ejournalgroupproject.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    private final TeacherQualificationRepository teacherQualificationRepository;

    @Override
    public TeacherDTO getTeacherById(Long teacherId) {
       Teacher teacher = teacherRepository.findById(teacherId)
               .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + teacherId + " not found"));
       return teacherMapper.EntityToDto(teacher);
    }

    @Override
    public List<TeacherDTO> getTeachersFromSchool(Long schoolId) {
        List<Teacher> teachers = teacherRepository.findAllBySchoolId(schoolId);
        return teachers
                .stream()
                .map(teacherMapper::EntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getTeachers() {
        List<Teacher> teacherEntities = teacherRepository.findAll();
        return teacherEntities
                .stream()
                .map(teacherMapper::EntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO createTeacher(@Valid TeacherDTO teacherDTO) {
        validateTeacherDTO(teacherDTO);
        Teacher teacher = teacherMapper.DtoToEntity(teacherDTO);
        return teacherMapper.EntityToDto(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDTO updateTeacher(@NotNull Long teacherId, @Valid TeacherDTO teacherDTO) {
        validateTeacherDTO(teacherDTO);
        Teacher existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + teacherId + " not found"));

        existingTeacher.setName(teacherDTO.getName());
        existingTeacher.setEmail(teacherDTO.getEmail());
        teacherMapper.mapSchoolIdToEntity(teacherDTO, existingTeacher);
        Teacher updatedTeacher = teacherRepository.save(existingTeacher);
        return teacherMapper.EntityToDto(updatedTeacher);
    }

    @Override
    public void deleteTeacher(Long teacherId) {
       Teacher teacher = teacherRepository.findById(teacherId)
               .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + teacherId + "not found"));
       teacherRepository.delete(teacher);

    }

    @Override
    public void validateTeacherDTO(TeacherDTO teacherDTO) {

        if (teacherDTO.getName() == null || teacherDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("The teacher name cannot be null or empty.");
        }

        if (teacherDTO.getEmail() == null || teacherDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("The teacher email cannot be null or empty.");
        }
    }

    @Override
    public TeacherDTO addQualificationToTeacher(Long teacherId, Long qualificationId) {
       Teacher teacher = teacherRepository.findById(teacherId)
               .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + teacherId + " not found"));

        TeacherQualification qualification = teacherQualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher Qualification with id " + qualificationId + " not found"));

        teacher.getTeacherQualifications().add(qualification);

        return teacherMapper.EntityToDto(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDTO deleteQualificationFromTeacher(Long teacherId, Long qualificationId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + teacherId + " not found"));

        TeacherQualification qualification = teacherQualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher Qualification with id " + qualificationId + " not found"));

        teacher.getTeacherQualifications().remove(qualification);

        return teacherMapper.EntityToDto(teacherRepository.save(teacher));
    }


}
