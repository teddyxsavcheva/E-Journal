package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;
import com.nbu.ejournalgroupproject.model.SchoolClass;
import com.nbu.ejournalgroupproject.model.Student;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.repository.StudentCurriculumRepository;
import com.nbu.ejournalgroupproject.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SchoolClassMapper {
    private final StudentCurriculumRepository studentCurriculumRepository;
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;

    public SchoolClassDTO mapEntityToDto(SchoolClass schoolClass){
        SchoolClassDTO schoolClassDTO = new SchoolClassDTO();
        schoolClassDTO.setId(schoolClass.getId());
        schoolClassDTO.setName(schoolClass.getName());
        schoolClassDTO.setYear(schoolClass.getYear());
        mapSchoolIdToSchoolClassDto(schoolClass, schoolClassDTO);
        mapStudentCurriculumToSchoolClassDto(schoolClass, schoolClassDTO);
        mapStudentsToSchoolClassDto(schoolClass, schoolClassDTO);

        return schoolClassDTO;
    }

    public SchoolClass mapDtoToEntity(SchoolClassDTO schoolClassDTO) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(schoolClassDTO.getId());
        schoolClass.setName(schoolClassDTO.getName());
        schoolClass.setYear(schoolClassDTO.getYear());
        mapSchoolIdToSchoolClassEntity(schoolClassDTO, schoolClass);
        mapStudentCurriculumToSchoolClassEntity(schoolClassDTO, schoolClass);
        mapStudentIdsToSchoolClassEntity(schoolClassDTO, schoolClass);
        return schoolClass;
    }

    private void mapSchoolIdToSchoolClassDto(SchoolClass schoolClass, SchoolClassDTO schoolClassDTO) {
        if (schoolClass.getSchool() != null) {
            schoolClassDTO.setSchoolId(schoolClass.getSchool().getId());
        }
        else throw new EntityNotFoundException("School not found.");

    }

    private void mapStudentCurriculumToSchoolClassDto(SchoolClass schoolClass, SchoolClassDTO schoolClassDTO) {
        if (schoolClass.getStudentCurriculums() != null) {
            schoolClassDTO.setStudentCurriculumId(schoolClass.getStudentCurriculums().getId());
        }
    }

    private void mapStudentsToSchoolClassDto(SchoolClass schoolClass, SchoolClassDTO schoolClassDTO) {
        List<Long> studentIds = schoolClass.getStudents().stream()
                .map(Student::getId)
                .collect(Collectors.toList());
        schoolClassDTO.setStudentIds(studentIds);
    }

    private void mapSchoolIdToSchoolClassEntity(SchoolClassDTO schoolClassDTO, SchoolClass schoolClass) {
        if (schoolClassDTO.getSchoolId() != null) {
            schoolClass.setSchool(schoolRepository.findById(schoolClassDTO.getSchoolId())
                    .orElseThrow(() -> new EntityNotFoundException("School not found")));
        }
    }

    private void mapStudentCurriculumToSchoolClassEntity(SchoolClassDTO schoolClassDTO, SchoolClass schoolClass) {
        if (schoolClassDTO.getStudentCurriculumId() != null) {
            schoolClass.setStudentCurriculums(studentCurriculumRepository.findById(schoolClassDTO.getStudentCurriculumId())
                    .orElseThrow(() -> new EntityNotFoundException("Student Curriculum not found")));
        }
    }

    private void mapStudentIdsToSchoolClassEntity(SchoolClassDTO schoolClassDTO, SchoolClass schoolClass) {
        List<Student> students = new ArrayList<>();
        if (schoolClassDTO.getStudentIds() != null) {
            for (Long studentId : schoolClassDTO.getStudentIds()) {
                studentRepository.findById(studentId).ifPresent(students::add);
            }
        }
        schoolClass.setStudents(students);
    }

    //    private static final ModelMapper modelMapper = new ModelMapper();
//
//    public static void updateSchoolClass(SchoolClassDTO schoolClassDTO, SchoolClass schoolClass) {
//        // Use ModelMapper to map non-null fields from DTO to entity
//        modelMapper.map(schoolClassDTO, schoolClass);
//
//        // Manually handle specific mappings or transformations
//        if (schoolClassDTO.getStudentIds() != null) {
//            // Example: Update student list based on student IDs
//            List<Student> students = schoolClassDTO.getStudentIds().stream()
//                    .map(studentId -> {
//                        Student student = new Student();
//                        student.setId(studentId);
//                        return student;
//                    })
//                    .collect(Collectors.toList());
//            schoolClass.setStudents(students);
//        }
//    }
}

