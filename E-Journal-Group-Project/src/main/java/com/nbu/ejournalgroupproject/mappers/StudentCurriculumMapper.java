package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.StudentCurriculumDTO;
import com.nbu.ejournalgroupproject.model.StudentCurriculum;
import com.nbu.ejournalgroupproject.repository.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudentCurriculumMapper {

    private final SchoolClassRepository schoolClassRepository;

    public StudentCurriculumDTO entityToDto(StudentCurriculum studentCurriculum){
        StudentCurriculumDTO studentCurriculumDTO = new StudentCurriculumDTO();
        studentCurriculumDTO.setId(studentCurriculum.getId());
        studentCurriculumDTO.setYear(studentCurriculum.getYear());
        studentCurriculumDTO.setSemester(studentCurriculum.getSemester());
        mapSchoolClassToSchoolClassId(studentCurriculum, studentCurriculumDTO);
        return studentCurriculumDTO;
    }

    public StudentCurriculum DtoToEntity(StudentCurriculumDTO studentCurriculumDTO){
        StudentCurriculum studentCurriculum = new StudentCurriculum();
        studentCurriculum.setId(studentCurriculumDTO.getId());
        studentCurriculum.setYear(studentCurriculumDTO.getYear());
        studentCurriculum.setSemester(studentCurriculumDTO.getSemester());
        mapSchoolClassIdToSchoolClass(studentCurriculumDTO, studentCurriculum);
        return studentCurriculum;
    }

    public void mapSchoolClassIdToSchoolClass(StudentCurriculumDTO studentCurriculumDTO, StudentCurriculum studentCurriculum){
        studentCurriculum.setSchoolClass(schoolClassRepository.findById(studentCurriculumDTO.getSchoolClassId())
                .orElseThrow(() -> new EntityNotFoundException("School Class with id " + studentCurriculumDTO.getSchoolClassId() + " not found")));

    }

    public void mapSchoolClassToSchoolClassId(StudentCurriculum studentCurriculum, StudentCurriculumDTO studentCurriculumDTO){
        studentCurriculumDTO.setSchoolClassId(studentCurriculum.getSchoolClass().getId());
    }
}
