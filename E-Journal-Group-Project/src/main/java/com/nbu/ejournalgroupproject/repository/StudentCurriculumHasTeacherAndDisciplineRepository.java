package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.StudentCurriculumHasTeacherAndDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCurriculumHasTeacherAndDisciplineRepository extends JpaRepository<StudentCurriculumHasTeacherAndDiscipline, Long> {

}
