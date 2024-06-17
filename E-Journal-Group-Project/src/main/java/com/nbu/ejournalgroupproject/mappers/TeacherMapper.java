package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeacherMapper {

    private final SchoolRepository schoolRepository;

    public TeacherDTO EntityToDto(Teacher teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(teacher.getId());
        teacherDTO.setName(teacher.getName());
        teacherDTO.setEmail(teacher.getEmail());

        teacherDTO.setSchoolId(getSchoolId(teacher));
        teacherDTO.setTeacherQualificationIds(getTeacherQualificationIds(teacher));

        return teacherDTO;
    }


    public Teacher DtoToEntity(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDTO.getId());
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());

        teacher.setSchool(getSchool(teacherDTO));
        teacher.setTeacherQualifications(getQualifications(teacherDTO));

        return teacher;
    }

    public Long getSchoolId(Teacher teacher) {
        return teacher.getSchool() != null ? teacher.getSchool().getId() : null;
    }

    public School getSchool(TeacherDTO teacherDTO) {
        if (teacherDTO.getSchoolId() != null) {
            return schoolRepository.findById(teacherDTO.getSchoolId())
                    .orElseThrow(() -> new EntityNotFoundException("School with id " + teacherDTO.getSchoolId() + " not found"));
        } else {
            return null;
        }
    }

    //TODO can Teacher Qualifications be null
    public List<Long> getTeacherQualificationIds(Teacher teacher) {
        return teacher.getTeacherQualifications().stream()
                .map(TeacherQualification::getId)
                .collect(Collectors.toList());
    }

    public List<TeacherQualification> getQualifications(TeacherDTO teacherDTO) {
        return teacherDTO.getTeacherQualificationIds() != null ? teacherDTO.getTeacherQualificationIds().stream()
                .map(id -> {
                    TeacherQualification qualification = new TeacherQualification();
                    qualification.setId(id);
                    return qualification;
                })
                .collect(Collectors.toList()) : null;
    }

    public void mapSchoolIdToEntity(TeacherDTO teacherDTO, Teacher teacher){

        if (teacherDTO.getSchoolId() != null) {
            Optional<School> schoolOptional = schoolRepository.findById(teacherDTO.getSchoolId());

            schoolOptional.ifPresent(teacher::setSchool);
        }
    }
}