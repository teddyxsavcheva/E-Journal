package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TeacherMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private final SchoolRepository schoolRepository;

    public TeacherMapper(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public TeacherDTO TeacherEntityToDto(Teacher teacher){
        TeacherDTO teacherDTO = modelMapper.map(teacher, TeacherDTO.class);
        mapSchoolEntityToId(teacher, teacherDTO);
        return teacherDTO;
    }

    public Teacher TeacherDtoToEntity(TeacherDTO teacherDTO){
        Teacher teacher = modelMapper.map(teacherDTO, Teacher.class);
        mapSchoolIdToEntity(teacherDTO, teacher);
        return teacher;
    }

    public void mapSchoolEntityToId(Teacher teacher, TeacherDTO teacherDTO){
        if (teacher.getSchool() != null){
            teacherDTO.setSchoolId(teacher.getSchool().getId());
        }
    }

    public void mapSchoolIdToEntity(TeacherDTO teacherDTO, Teacher teacher){
        if (teacherDTO.getSchoolId() != null) {
            Optional<School> schoolOptional = schoolRepository.findById(teacherDTO.getSchoolId());

            if (schoolOptional.isPresent()) {
                teacher.setSchool(schoolOptional.get());
            } else {
                throw new EntityNotFoundException("School with ID " + teacherDTO.getSchoolId() + " is not found");
            }
        }
    }
}
