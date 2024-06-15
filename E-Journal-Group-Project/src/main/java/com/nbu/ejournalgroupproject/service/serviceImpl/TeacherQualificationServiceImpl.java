package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.mappers.TeacherQualificationMapper;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import com.nbu.ejournalgroupproject.repository.DisciplineRepository;
import com.nbu.ejournalgroupproject.repository.TeacherQualificationRepository;
import com.nbu.ejournalgroupproject.repository.TeacherRepository;
import com.nbu.ejournalgroupproject.service.TeacherQualificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeacherQualificationServiceImpl implements TeacherQualificationService {

    private TeacherQualificationRepository teacherQualificationRepository;
    private TeacherRepository teacherRepository;
    private TeacherQualificationMapper teacherQualificationMapper;
    private DisciplineRepository disciplineRepository;

    @Override
    public List<TeacherQualificationDto> getAllTeacherQualifications() {

        List<TeacherQualification> teacherQualifications = teacherQualificationRepository.findAll();

        return teacherQualifications.stream()
                .map(teacherQualificationMapper::convertToDto)
                .collect(Collectors.toList());

    }

    @Override
    public TeacherQualificationDto getTeacherQualificationById(Long id) {

        TeacherQualification teacherQualification = teacherQualificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher Qualification found with id + " + id));

        return teacherQualificationMapper.convertToDto(teacherQualification);

    }

    @Override
    public TeacherQualificationDto createTeacherQualification(TeacherQualificationDto dto) {

        validateTeacherQualificationDto(dto);

        TeacherQualification teacherQualification = teacherQualificationMapper.convertToEntity(dto);

        return teacherQualificationMapper.convertToDto(teacherQualificationRepository.save(teacherQualification));
    }

    @Override
    public TeacherQualificationDto updateTeacherQualification(TeacherQualificationDto dto, Long id) {

        validateTeacherQualificationDto(dto);

        TeacherQualification teacherQualification = teacherQualificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher Qualification found with id + " + id));

        teacherQualification.setQualificationEnum(dto.getQualificationEnum());

        return teacherQualificationMapper.convertToDto(teacherQualificationRepository.save(teacherQualification));
    }

    @Override
    public void deleteTeacherQualification(Long id) {

        TeacherQualification teacherQualification = teacherQualificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher Qualification found with id + " + id));

        teacherQualificationRepository.delete(teacherQualification);

    }

    @Override
    public void validateTeacherQualificationDto(TeacherQualificationDto dto) {

        if(dto.getQualificationEnum() == null) {
            throw new IllegalArgumentException("Teacher Qualification Enum cannot be null");
        }

    }

    @Override
    public TeacherQualificationDto addTeacherToQualification(Long qualificationId, Long teacherId) {

        TeacherQualification qualification = teacherQualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher Qualification found with id " + qualificationId));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher found with id " + teacherId));

        qualification.getTeachers().add(teacher);

        return teacherQualificationMapper.convertToDto(teacherQualificationRepository.save(qualification));
    }

    @Override
    public TeacherQualificationDto removeTeacherFromQualification(Long qualificationId, Long teacherId) {

        TeacherQualification qualification = teacherQualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher Qualification found with id " + qualificationId));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher found with id " + teacherId));

        qualification.getTeachers().remove(teacher);

        return teacherQualificationMapper.convertToDto(teacherQualificationRepository.save(qualification));

    }

    @Override
    public TeacherQualificationDto addDisciplineToQualification(Long qualificationId, Long disciplineId) {

        TeacherQualification qualification = teacherQualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher Qualification found with id " + qualificationId));

        Discipline discipline = disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + disciplineId));

        qualification.getDisciplines().add(discipline);

        return teacherQualificationMapper.convertToDto(teacherQualificationRepository.save(qualification));

    }

    @Override
    public TeacherQualificationDto removeDisciplineFromQualification(Long qualificationId, Long disciplineId) {

        TeacherQualification qualification = teacherQualificationRepository.findById(qualificationId)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher Qualification found with id " + qualificationId));

        Discipline discipline = disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id " + disciplineId));

        qualification.getDisciplines().remove(discipline);

        return teacherQualificationMapper.convertToDto(teacherQualificationRepository.save(qualification));

    }
}
