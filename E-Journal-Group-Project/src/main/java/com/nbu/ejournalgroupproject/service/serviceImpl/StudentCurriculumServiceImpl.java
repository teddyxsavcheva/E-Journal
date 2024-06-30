package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.StudentCurriculumDTO;
import com.nbu.ejournalgroupproject.mappers.StudentCurriculumMapper;
import com.nbu.ejournalgroupproject.model.SchoolClass;
import com.nbu.ejournalgroupproject.model.StudentCurriculum;
import com.nbu.ejournalgroupproject.repository.SchoolClassRepository;
import com.nbu.ejournalgroupproject.repository.StudentCurriculumRepository;
import com.nbu.ejournalgroupproject.service.StudentCurriculumService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentCurriculumServiceImpl implements StudentCurriculumService {

    private final StudentCurriculumRepository studentCurriculumRepository;
    private final StudentCurriculumMapper studentCurriculumMapper;
    private final SchoolClassRepository schoolClassRepository;

    @Override
    public List<StudentCurriculumDTO> getStudentCurriculums() {
        List<StudentCurriculum> studentCurriculums = studentCurriculumRepository.findAll();
        return studentCurriculums
                .stream()
                .map(studentCurriculumMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentCurriculumDTO getStudentCurriculum(Long id) {
        StudentCurriculum studentCurriculum = studentCurriculumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student Curriculum with id " + id + " not found"));
        return studentCurriculumMapper.entityToDto(studentCurriculum);
    }

    @Override
    public StudentCurriculumDTO createStudentCurriculum(@Valid StudentCurriculumDTO studentCurriculumDTO) {
        validateStudentCurriculumDTO(studentCurriculumDTO);
        StudentCurriculum studentCurriculum = studentCurriculumMapper.DtoToEntity(studentCurriculumDTO);
        return studentCurriculumMapper.entityToDto(studentCurriculumRepository.save(studentCurriculum));
    }

    @Override
    public StudentCurriculumDTO updateStudentCurriculum(@NotNull Long id, @Valid StudentCurriculumDTO newCurriculumDTO) {
        validateStudentCurriculumDTO(newCurriculumDTO);
        StudentCurriculum existingStudentCurriculum = studentCurriculumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student Curriculum with id " + id + " not found"));

        existingStudentCurriculum.setYear(newCurriculumDTO.getYear());
        existingStudentCurriculum.setSemester(newCurriculumDTO.getSemester());
        updateStudentCurriculumSchoolClass(existingStudentCurriculum, newCurriculumDTO);

        return studentCurriculumMapper.entityToDto(studentCurriculumRepository.save(existingStudentCurriculum));

    }

    private void updateStudentCurriculumSchoolClass(StudentCurriculum existingStudentCurriculum, StudentCurriculumDTO newCurriculumDTO) {
        Long schoolClassId = newCurriculumDTO.getSchoolClassId();
        if(schoolClassId != null){
            SchoolClass schoolClass = schoolClassRepository.findById(schoolClassId)
                    .orElseThrow(() -> new EntityNotFoundException("School with id " + schoolClassId + " not found"));
            existingStudentCurriculum.setSchoolClass(schoolClass);
        }
    }

    @Override
    public void deleteStudentCurriculum(Long id) {
        StudentCurriculum studentCurriculum = studentCurriculumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("STudent Curriculum with id " + id + " not found"));

        studentCurriculumRepository.delete(studentCurriculum);
    }

    @Override
    public void validateStudentCurriculumDTO(StudentCurriculumDTO studentCurriculumDTO) {
        if (studentCurriculumDTO.getSchoolClassId() == 0 ){
            throw new IllegalArgumentException("The School class cannot be zero.");
        }

    }

    @Override
    public StudentCurriculumDTO getStudentCurriculumByClassId(long id) {
        return studentCurriculumMapper.entityToDto(studentCurriculumRepository.getStudentCurriculumBySchoolClassId(id));
    }
}
