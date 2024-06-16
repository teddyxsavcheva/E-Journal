package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeacherMapper {
//    private final ModelMapper modelMapper = new ModelMapper();

    private final SchoolRepository schoolRepository;

    public TeacherDTO EntityToDto(Teacher teacher) {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());
        dto.setEmail(teacher.getEmail());
        dto.setSchoolId(teacher.getSchool() != null ? teacher.getSchool().getId() : null);

        if (teacher.getTeacherQualifications() != null) {
            List<Long> qualificationIds = teacher.getTeacherQualifications().stream()
                    .map(TeacherQualification::getId)
                    .collect(Collectors.toList());
            dto.setTeacherQualificationIds(qualificationIds);
        }

        return dto;
    }

    public Teacher DtoToEntity(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDTO.getId());
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());

        teacher.setSchool(schoolRepository.findById(teacherDTO.getSchoolId()).orElseGet(null));

        if (teacherDTO.getTeacherQualificationIds() != null) {
            List<TeacherQualification> qualifications = teacherDTO.getTeacherQualificationIds().stream()
                    .map(id -> {
                        TeacherQualification qualification = new TeacherQualification();
                        qualification.setId(id);
                        return qualification;
                    })
                    .collect(Collectors.toList());
            teacher.setTeacherQualifications(qualifications);
        }

        return teacher;
    }

//    public void mapSchoolEntityToId(Teacher teacher, TeacherDTO teacherDTO){
//        if (teacher.getSchool() != null){
//            teacherDTO.setSchoolId(teacher.getSchool().getId());
//        }
//    }

    public void mapSchoolIdToEntity(TeacherDTO teacherDTO, Teacher teacher){

        if (teacherDTO.getSchoolId() != null) {
            Optional<School> schoolOptional = schoolRepository.findById(teacherDTO.getSchoolId());

            schoolOptional.ifPresent(teacher::setSchool);
        }
    }




}
