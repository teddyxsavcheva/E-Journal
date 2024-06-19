package com.nbu.ejournalgroupproject.mappers;

import com.nbu.ejournalgroupproject.dto.StudentCurriculumHasTeacherAndDisciplineDto;

import com.nbu.ejournalgroupproject.model.Discipline;
import com.nbu.ejournalgroupproject.model.StudentCurriculum;
import com.nbu.ejournalgroupproject.model.StudentCurriculumHasTeacherAndDiscipline;
import com.nbu.ejournalgroupproject.model.Teacher;
import com.nbu.ejournalgroupproject.repository.DisciplineRepository;
import com.nbu.ejournalgroupproject.repository.StudentCurriculumHasTeacherAndDisciplineRepository;
import com.nbu.ejournalgroupproject.repository.StudentCurriculumRepository;
import com.nbu.ejournalgroupproject.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurriculumHasTeacherAndDisciplineMapper {

    private final StudentCurriculumHasTeacherAndDisciplineRepository curriculumHasTeacherAndDisciplineRepository;
    private final TeacherRepository teacherRepository;
    private final DisciplineRepository disciplineRepository;
    private final StudentCurriculumRepository curriculumRepository;

    public StudentCurriculumHasTeacherAndDisciplineDto convertToDto (StudentCurriculumHasTeacherAndDiscipline entity) {

        StudentCurriculumHasTeacherAndDisciplineDto dto = new StudentCurriculumHasTeacherAndDisciplineDto();

        dto.setId(entity.getId());

        // Created methods for those
        setDisciplineIdInDto(dto, entity);
        setTeacherIdInDto(dto, entity);
        setCurriculumIdInDto(dto, entity);

        return dto;
    }

    public StudentCurriculumHasTeacherAndDiscipline convertToEntity (StudentCurriculumHasTeacherAndDisciplineDto dto) {

        StudentCurriculumHasTeacherAndDiscipline entity = new StudentCurriculumHasTeacherAndDiscipline();

        // Checks if the discipline is present in the DB first
        Discipline discipline = fetchDiscipline(dto.getDisciplineId());
        entity.setDiscipline(discipline);

        // Checks if the teacher is present in the DB first
        Teacher teacher = fetchTeacher(dto.getTeacherId());
        entity.setTeacher(teacher);

        // Checks if the curriculum is present in the DB first
        StudentCurriculum curriculum = fetchStudentCurriculum(dto.getCurriculumId());
        entity.setStudentCurriculum(curriculum);

        return entity;

    }

    public void setDisciplineIdInDto (StudentCurriculumHasTeacherAndDisciplineDto dto, StudentCurriculumHasTeacherAndDiscipline entity) {

        Discipline discipline = entity.getDiscipline();

        if (discipline != null) {
            if (disciplineRepository.existsById(discipline.getId())) {
                dto.setDisciplineId(discipline.getId());
            } else {
                throw new EntityNotFoundException("No Discipline found with id " + discipline.getId());
            }
        } else {
            throw new EntityNotFoundException("Discipline object is null for entity with id " + entity.getId());
        }

    }

    public void setTeacherIdInDto (StudentCurriculumHasTeacherAndDisciplineDto dto, StudentCurriculumHasTeacherAndDiscipline entity) {

        Teacher teacher = entity.getTeacher();

        if (teacherRepository.existsById(teacher.getId())) {
            dto.setTeacherId(teacher.getId());
        }
        else {
            throw new EntityNotFoundException("No Teacher found with id " + teacher.getId());
        }

    }

    public void setCurriculumIdInDto (StudentCurriculumHasTeacherAndDisciplineDto dto, StudentCurriculumHasTeacherAndDiscipline entity) {

        StudentCurriculum curriculum = entity.getStudentCurriculum();

        if (curriculumRepository.existsById(curriculum.getId())) {
            dto.setCurriculumId(curriculum.getId());
        }
        else {
            throw new EntityNotFoundException("No StudentCurriculum found with id " + curriculum.getId());
        }

    }

    public Discipline fetchDiscipline(Long disciplineId) {

        return disciplineRepository.findById(disciplineId)
                .orElseThrow(() -> new EntityNotFoundException("No Discipline with id " + disciplineId));

    }

    public Teacher fetchTeacher(Long teacherId) {

        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("No Teacher with id " + teacherId));

    }

    public StudentCurriculum fetchStudentCurriculum(Long curriculumId) {

        return curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new EntityNotFoundException("No Curriculum with id " + curriculumId));

    }

}
