package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.StudentCurriculumHasTeacherAndDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCurriculumHasTeacherAndDisciplineRepository extends JpaRepository<StudentCurriculumHasTeacherAndDiscipline, Long> {
    List<StudentCurriculumHasTeacherAndDiscipline> findAllByStudentCurriculumId(long id);

}
