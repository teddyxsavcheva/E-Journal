package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.SchoolClassDTO;
import com.nbu.ejournalgroupproject.mappers.SchoolClassMapper;
import com.nbu.ejournalgroupproject.model.SchoolClass;
import com.nbu.ejournalgroupproject.repository.SchoolClassRepository;
import com.nbu.ejournalgroupproject.repository.SchoolRepository;
import com.nbu.ejournalgroupproject.repository.StudentCurriculumRepository;
import com.nbu.ejournalgroupproject.repository.StudentRepository;
import com.nbu.ejournalgroupproject.service.SchoolClassService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final SchoolClassMapper schoolClassMapper;
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final StudentCurriculumRepository studentCurriculumRepository;

    @Override
    public List<SchoolClassDTO> getSchoolClasses() {
        List<SchoolClass> schoolClasses = schoolClassRepository.findAll();
        return schoolClasses
                .stream()
                .map(schoolClassMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SchoolClassDTO getSchoolClas(Long id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School Class with id " + id + " not found"));
        return schoolClassMapper.mapEntityToDto(schoolClass);
    }

    @Override
    public SchoolClassDTO createSchoolClass(SchoolClassDTO schoolClassDTO) {
        validateSchoolClassDTO(schoolClassDTO);


        SchoolClass schoolClass = schoolClassMapper.mapDtoToEntity(schoolClassDTO);
        return schoolClassMapper.mapEntityToDto(schoolClassRepository.save(schoolClass));
    }

//    @Override
//    public SchoolClassDTO updateSchoolClass(Long id, SchoolClassDTO newSchoolClass) {
//        validateSchoolClassDTO(newSchoolClass);
//
//        SchoolClass existingSchoolClass = schoolClassRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("School Class with id " + id + " not found."));
//
//        existingSchoolClass.setId(newSchoolClass.getId());
//        existingSchoolClass.setYear(newSchoolClass.getYear());
//
//        existingSchoolClass.setSchool(schoolRepository.findById(newSchoolClass.getSchoolId())
//                .orElseThrow(() -> new EntityNotFoundException("School with id " + newSchoolClass.getSchoolId() + " not found.")));
//
//        existingSchoolClass.setStudentCurriculums(studentCurriculumRepository.findById(newSchoolClass.getStudentCurriculumId())
//                .orElseThrow(() -> new EntityNotFoundException("Student Curriculum with id " + newSchoolClass.getStudentCurriculumId() + " not found.")));
//
//        existingSchoolClass.setStudents(studentRepository.findAllById(newSchoolClass.getStudentIds()));
//
//        return schoolClassMapper.mapEntityToDto(schoolClassRepository.save(existingSchoolClass));
//    }

    @Override
    public void deleteSchoolClass(Long id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School Class with id " + id + " not found."));
        schoolClassRepository.delete(schoolClass);

    }

    @Override
    public void validateSchoolClassDTO(SchoolClassDTO schoolClassDTO) {
        if (schoolClassDTO.getSchoolId() == null) {
            throw new IllegalArgumentException("The School cannot be null.");
        }

        if (schoolClassDTO.getStudentCurriculumId() == null) {
            throw new IllegalArgumentException("The Student Curriculum cannot be null.");
        }
    }

//TODO -> METHODS FOR STUDENTS -> ADD, REMOVE
    @Override
    public SchoolClassDTO addStudentById(Long id) {
        return null;
    }
    @Override
    public SchoolClassDTO removeStudentById(Long id) {
        return null;
    }
}
