package com.nbu.ejournalgroupproject.service.serviceImpl;

import com.nbu.ejournalgroupproject.dto.StudentCurriculumHasTeacherAndDisciplineDto;
import com.nbu.ejournalgroupproject.mappers.CurriculumHasTeacherAndDisciplineMapper;
import com.nbu.ejournalgroupproject.model.*;
import com.nbu.ejournalgroupproject.repository.DisciplineRepository;
import com.nbu.ejournalgroupproject.repository.StudentCurriculumHasTeacherAndDisciplineRepository;
import com.nbu.ejournalgroupproject.repository.StudentCurriculumRepository;
import com.nbu.ejournalgroupproject.repository.TeacherRepository;
import com.nbu.ejournalgroupproject.service.StudentCurriculumHasTeacherAndDisciplineService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentCurriculumHasTeacherAndDisciplineServiceImpl implements StudentCurriculumHasTeacherAndDisciplineService {

    private final StudentCurriculumHasTeacherAndDisciplineRepository repository;
    private final TeacherRepository teacherRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentCurriculumRepository studentCurriculumRepository;
    private final CurriculumHasTeacherAndDisciplineMapper mapper;

    @Override
    public List<StudentCurriculumHasTeacherAndDisciplineDto> getAllCurriculumHasTeacherAndDiscipline() {

        List<StudentCurriculumHasTeacherAndDiscipline> list = repository.findAll();

        return list.stream()
                .map(mapper::convertToDto)
                .toList();

    }

    @Override
    public StudentCurriculumHasTeacherAndDisciplineDto getCurriculumHasTeacherAndDisciplineById(Long id) {

        StudentCurriculumHasTeacherAndDiscipline entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Curriculum with teacher and Discipline found with id " + id));

        return mapper.convertToDto(entity);
    }

    @Override
    public StudentCurriculumHasTeacherAndDisciplineDto createCurriculumHasTeacherAndDiscipline(StudentCurriculumHasTeacherAndDisciplineDto dto) {

        validateCurriculumHasTeacherAndDisciplineDto(dto);

        StudentCurriculumHasTeacherAndDiscipline entity = new StudentCurriculumHasTeacherAndDiscipline();

        populateAndValidateEntity(entity, dto);

        return mapper.convertToDto(repository.save(entity));

    }

    @Override
    public StudentCurriculumHasTeacherAndDisciplineDto updateCurriculumHasTeacherAndDiscipline(StudentCurriculumHasTeacherAndDisciplineDto dto, Long id) {

        validateCurriculumHasTeacherAndDisciplineDto(dto);

        StudentCurriculumHasTeacherAndDiscipline entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Curriculum with teacher and Discipline found with id " + dto.getId()));

        populateAndValidateEntity(entity, dto);

        return mapper.convertToDto(repository.save(entity));
    }

    @Override
    public void deleteCurriculumHasTeacherAndDiscipline(Long id) {

        StudentCurriculumHasTeacherAndDiscipline entity = repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("No Curriculum with teacher and Discipline found with id " + id));

        repository.deleteById(id);
    }

    private void populateAndValidateEntity(StudentCurriculumHasTeacherAndDiscipline entity, StudentCurriculumHasTeacherAndDisciplineDto dto) {

        // Checking if the teacher is qualified for the discipline before adding the pair in the curriculum
        if (!teacherRepository.isTeacherQualifiedForDiscipline(dto.getTeacherId(), dto.getDisciplineId())) {
            throw new IllegalArgumentException("The teacher is not qualified for the discipline.");
        }

        Discipline discipline = disciplineRepository.findById(dto.getDisciplineId())
                .orElseThrow(() -> new EntityNotFoundException("No Discipline with id " + dto.getDisciplineId()));
        entity.setDiscipline(discipline);

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("No Teacher with id " + dto.getTeacherId()));
        entity.setTeacher(teacher);

        StudentCurriculum curriculum = studentCurriculumRepository.findById(dto.getCurriculumId())
                .orElseThrow(() -> new EntityNotFoundException("No Curriculum with id " + dto.getCurriculumId()));
        entity.setStudentCurriculum(curriculum);
    }

    @Override
    public void validateCurriculumHasTeacherAndDisciplineDto(StudentCurriculumHasTeacherAndDisciplineDto dto) {

        if (dto.getDisciplineId() == null || dto.getDisciplineId() == 0) {
            throw new IllegalArgumentException("The discipline id cannot be null or zero.");
        }

        if (dto.getTeacherId() == null || dto.getTeacherId() == 0) {
            throw new IllegalArgumentException("The teacher id cannot be null or zero.");
        }

        if (dto.getCurriculumId() == null || dto.getCurriculumId() == 0) {
            throw new IllegalArgumentException("The curriculum id cannot be null or zero.");
        }

    }

}
