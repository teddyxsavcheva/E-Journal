package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.mappers.TeacherQualificationMapper;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import com.nbu.ejournalgroupproject.repository.TeacherQualificationRepository;
import com.nbu.ejournalgroupproject.service.TeacherQualificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TeacherQualificationServiceImpl implements TeacherQualificationService {

    private TeacherQualificationRepository teacherQualificationRepository;
    private TeacherQualificationMapper teacherQualificationMapper;

    @Override
    public List<TeacherQualificationDto> getAllTeacherQualifications() {

        List<TeacherQualification> teacherQualifications = teacherQualificationRepository.findAll();

        return teacherQualifications.stream()
                .map(teacherQualificationMapper::convertToDto)
                .toList();

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

        teacherQualification.setQualificationEnum(teacherQualification.getQualificationEnum());

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
}
