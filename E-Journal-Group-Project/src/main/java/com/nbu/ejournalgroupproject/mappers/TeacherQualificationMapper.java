package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import com.nbu.ejournalgroupproject.repository.DisciplineRepository;
import com.nbu.ejournalgroupproject.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherQualificationMapper {

    private final TeacherRepository teacherRepository;
    private final DisciplineRepository disciplineRepository;

    public TeacherQualificationDto convertToDto(TeacherQualification teacherQualification) {

        TeacherQualificationDto teacherQualificationDto = new TeacherQualificationDto();

        teacherQualificationDto.setId(teacherQualification.getId());
        teacherQualificationDto.setQualificationEnum(teacherQualification.getQualificationEnum());

        if (teacherQualification.getTeachers() != null) {
            teacherQualificationDto.setTeacherIds(teacherQualification.getTeachers().stream()
                    .map(Teacher::getId)
                    .collect(Collectors.toList()));
        }

        if (teacherQualification.getDisciplines() != null) {
            teacherQualificationDto.setDisciplineIds(teacherQualification.getDisciplines().stream()
                    .map(Discipline::getId)
                    .collect(Collectors.toList()));
        }

        return teacherQualificationDto;

    }

    public TeacherQualification convertToEntity(TeacherQualificationDto teacherQualificationDto) {

        TeacherQualification teacherQualification = new TeacherQualification();
        teacherQualification.setQualificationEnum(teacherQualificationDto.getQualificationEnum());

        if (teacherQualificationDto.getTeacherIds() != null) {
            teacherQualification.setTeachers(teacherQualificationDto.getTeacherIds().stream()
                    .map(id -> teacherRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("No Teacher found with id: " + id)))
                    .collect(Collectors.toList()));
        }

        if (teacherQualificationDto.getDisciplineIds() != null) {
            teacherQualification.setDisciplines(teacherQualificationDto.getDisciplineIds().stream()
                    .map(id -> disciplineRepository.findById(id)
                            .orElseThrow(() -> new EntityNotFoundException("No Discipline found with id: " + id)))
                    .collect(Collectors.toList()));
        }

        return teacherQualification;

    }
}
