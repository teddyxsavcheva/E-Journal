package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.SchoolDTO;
import com.nbu.ejournalgroupproject.dto.TeacherDTO;
import com.nbu.ejournalgroupproject.model.Headmaster;
import com.nbu.ejournalgroupproject.model.School;
import com.nbu.ejournalgroupproject.model.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SchoolMapper {

    private final ModelMapper modelMapper = new ModelMapper();
    private final TeacherMapper teacherMapper;

    public SchoolMapper(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    public SchoolDTO SchoolEntityToDto(School school){
        SchoolDTO schoolDTO = modelMapper.map(school, SchoolDTO.class);
        List<TeacherDTO> teacherDTOS = school.getTeachers()
                .stream()
                .map(teacherMapper::TeacherEntityToDto)
                .collect(Collectors.toList());
        schoolDTO.setTeachers(teacherDTOS);

        //TODO TEDI -> TOVA VUOBSHTE TRYABVA LI MI -> RABOTI I BEZ NEGO
//        if(school.getHeadmaster() != null){
//            schoolDTO.setHeadmasterId(school.getHeadmaster().getId());
//        }
//        else{
//            throw new EntityNotFoundException("Headmaster with ID " + school.getHeadmaster().getId() + "not found." );
//        }
        return schoolDTO;
    }

    public School SchoolDtoToEntity(SchoolDTO schoolDTO){
        School school = modelMapper.map(schoolDTO, School.class);
        List<Teacher> teachers = schoolDTO.getTeachers()
                .stream()
                .map(teacherMapper::TeacherDtoToEntity)
                .collect(Collectors.toList());
        school.setTeachers(teachers);

        return school;
    }

//    public void mapHeadmasterIdToEntity(SchoolDTO schoolDTO, School school){
//        if(schoolDTO.getHeadmasterId() != null){
//            Optional<Headmaster> headmasterOptional = headmasterRepository.findById(schoolDTO.getHeadmasterId()); //TODO TODO DOVURSHI GO
//        }
//    }
}
