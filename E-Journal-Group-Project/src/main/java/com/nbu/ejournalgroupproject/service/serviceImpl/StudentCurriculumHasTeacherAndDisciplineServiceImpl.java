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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentCurriculumHasTeacherAndDisciplineServiceImpl implements StudentCurriculumHasTeacherAndDisciplineService {

    private final StudentCurriculumHasTeacherAndDisciplineRepository repository;
    private final TeacherRepository teacherRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentCurriculumRepository studentCurriculumRepository;
    private final CurriculumHasTeacherAndDisciplineMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(StudentCurriculumHasTeacherAndDisciplineServiceImpl.class);

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public List<StudentCurriculumHasTeacherAndDisciplineDto> getAllCurriculumHasTeacherAndDiscipline() {

        List<StudentCurriculumHasTeacherAndDiscipline> list = repository.findAll();

        return list.stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR','HEADMASTER', 'TEACHER', 'STUDENT', 'CAREGIVER')")
    @Override
    public StudentCurriculumHasTeacherAndDisciplineDto getCurriculumHasTeacherAndDisciplineById(@NotNull Long id) {

        StudentCurriculumHasTeacherAndDiscipline entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Curriculum with teacher and Discipline found with id " + id));

        return mapper.convertToDto(entity);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public StudentCurriculumHasTeacherAndDisciplineDto createCurriculumHasTeacherAndDiscipline(@Valid StudentCurriculumHasTeacherAndDisciplineDto dto) {

        validateCurriculumHasTeacherAndDisciplineDto(dto);

        StudentCurriculumHasTeacherAndDiscipline entity = new StudentCurriculumHasTeacherAndDiscipline();

        // Validate if the teacher is from the same school as the school class that has the curriculum
        validateTeacherAndCurriculumSchool(dto);

        // Validate if the teacher has the qualification to teach the discipline
        validateTeacherDisciplineRelation(entity, dto);

        Discipline discipline = disciplineRepository.findById(dto.getDisciplineId())
                .orElseThrow(() -> new EntityNotFoundException("No Discipline with id " + dto.getDisciplineId()));
        entity.setDiscipline(discipline);

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("No Teacher with id " + dto.getTeacherId()));
        entity.setTeacher(teacher);

        StudentCurriculum curriculum = studentCurriculumRepository.findById(dto.getCurriculumId())
                .orElseThrow(() -> new EntityNotFoundException("No Curriculum with id " + dto.getCurriculumId()));
        entity.setStudentCurriculum(curriculum);

        return mapper.convertToDto(repository.save(entity));

    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public StudentCurriculumHasTeacherAndDisciplineDto updateCurriculumHasTeacherAndDiscipline(@Valid StudentCurriculumHasTeacherAndDisciplineDto dto,@NotNull Long id) {

        validateCurriculumHasTeacherAndDisciplineDto(dto);

        StudentCurriculumHasTeacherAndDiscipline entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Curriculum with teacher and Discipline found with id " + dto.getId()));

        // Validate if the teacher is from the same school as the school class that has the curriculum
        validateTeacherAndCurriculumSchool(dto);

        // Validate if the teacher has the qualification to teach the discipline
        validateTeacherDisciplineRelation(entity, dto);

        Discipline discipline = disciplineRepository.findById(dto.getDisciplineId())
                .orElseThrow(() -> new EntityNotFoundException("No Discipline with id " + dto.getDisciplineId()));
        entity.setDiscipline(discipline);

        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("No Teacher with id " + dto.getTeacherId()));
        entity.setTeacher(teacher);

        StudentCurriculum curriculum = studentCurriculumRepository.findById(dto.getCurriculumId())
                .orElseThrow(() -> new EntityNotFoundException("No Curriculum with id " + dto.getCurriculumId()));
        entity.setStudentCurriculum(curriculum);

        return mapper.convertToDto(repository.save(entity));
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Override
    public void deleteCurriculumHasTeacherAndDiscipline(@NotNull Long id) {

        StudentCurriculumHasTeacherAndDiscipline entity = repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("No Curriculum with teacher and Discipline found with id " + id));

        repository.deleteById(id);
    }

    private void validateTeacherAndCurriculumSchool(StudentCurriculumHasTeacherAndDisciplineDto dto) {

        Long teacherId = dto.getTeacherId();
        Long curriculumId = dto.getCurriculumId();

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher with id " + teacherId));

        StudentCurriculum curriculum = studentCurriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new EntityNotFoundException("No Curriculum with id " + curriculumId));

        School teacherSchool = teacher.getSchool();
        School curriculumSchool = curriculum.getSchoolClass().getSchool();

        if (!teacherSchool.equals(curriculumSchool)) {
            throw new IllegalArgumentException("Teacher and Curriculum must belong to the same school.");
        }

    }

    private void validateTeacherDisciplineRelation(StudentCurriculumHasTeacherAndDiscipline entity, StudentCurriculumHasTeacherAndDisciplineDto dto) {

        // Checking if the teacher is qualified for the discipline before adding the pair in the curriculum
        if (!teacherRepository.isTeacherQualifiedForDiscipline(dto.getTeacherId(), dto.getDisciplineId())) {
            throw new IllegalArgumentException("The teacher is not qualified for the discipline.");
        }

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
