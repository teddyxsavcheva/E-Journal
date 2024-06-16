package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;
import com.nbu.ejournalgroupproject.model.SchoolClass;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class SchoolClassMapper {
    private final SchoolRepository schoolRepository;

    public SchoolClassDTO mapEntityToDto(SchoolClass schoolClass){
        SchoolClassDTO schoolClassDTO = new SchoolClassDTO();
        schoolClassDTO.setId(schoolClass.getId());
        schoolClassDTO.setName(schoolClass.getName());
        schoolClassDTO.setYear(schoolClass.getYear());
        mapSchoolIdToSchoolClassDto(schoolClass, schoolClassDTO);
//        mapStudentCurriculumToSchoolClassDto(schoolClass, schoolClassDTO);

        return schoolClassDTO;
    }

    public SchoolClass mapDtoToEntity(SchoolClassDTO schoolClassDTO) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(schoolClassDTO.getId());
        schoolClass.setName(schoolClassDTO.getName());
        schoolClass.setYear(schoolClassDTO.getYear());
        mapSchoolIdToSchoolClassEntity(schoolClassDTO, schoolClass);
//        mapStudentCurriculumToSchoolClassEntity(schoolClassDTO, schoolClass);
        return schoolClass;
    }

    private void mapSchoolIdToSchoolClassDto(SchoolClass schoolClass, SchoolClassDTO schoolClassDTO) {
        if (schoolClass.getSchool() != null) {
            schoolClassDTO.setSchoolId(schoolClass.getSchool().getId());
        }
        else throw new EntityNotFoundException("School not found.");

    }

//    private void mapStudentCurriculumToSchoolClassDto(SchoolClass schoolClass, SchoolClassDTO schoolClassDTO) {
//        if (schoolClass.getStudentCurriculums() != null) {
//            schoolClassDTO.setStudentCurriculumId(schoolClass.getStudentCurriculums().getId());
//        }
//    }

    private void mapSchoolIdToSchoolClassEntity(SchoolClassDTO schoolClassDTO, SchoolClass schoolClass) {
        if (schoolClassDTO.getSchoolId() != null) {
            schoolClass.setSchool(schoolRepository.findById(schoolClassDTO.getSchoolId())
                    .orElseThrow(() -> new EntityNotFoundException("School not found")));
        }
    }

//    private void mapStudentCurriculumToSchoolClassEntity(SchoolClassDTO schoolClassDTO, SchoolClass schoolClass) {
//        if (schoolClassDTO.getStudentCurriculumId() != null) {
//            schoolClass.setStudentCurriculums(studentCurriculumRepository.findById(schoolClassDTO.getStudentCurriculumId())
//                    .orElseThrow(() -> new EntityNotFoundException("Student Curriculum not found")));
//        }
//    }
}

