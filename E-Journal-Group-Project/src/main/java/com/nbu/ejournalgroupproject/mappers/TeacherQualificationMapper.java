package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.TeacherQualificationDto;
import com.nbu.ejournalgroupproject.model.TeacherQualification;
import org.springframework.stereotype.Service;

@Service
public class TeacherQualificationMapper {

    public TeacherQualificationDto convertToDto(TeacherQualification teacherQualification) {

        TeacherQualificationDto teacherQualificationDto = new TeacherQualificationDto();

        teacherQualificationDto.setId(teacherQualification.getId());
        teacherQualificationDto.setQualificationEnum(teacherQualification.getQualificationEnum());

        return teacherQualificationDto;

    }

    public TeacherQualification convertToEntity(TeacherQualificationDto teacherQualificationDto) {

        TeacherQualification teacherQualification = new TeacherQualification();

        teacherQualification.setQualificationEnum(teacherQualificationDto.getQualificationEnum());
        return teacherQualification;

    }
}
