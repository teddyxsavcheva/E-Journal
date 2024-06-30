package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    @Query("SELECT d FROM Student s " +
            "JOIN s.schoolClass sc " +
            "JOIN sc.studentCurriculums cur " +
            "JOIN cur.curriculumHasTeacherAndDisciplineList tads " +
            "JOIN tads.discipline d " +
            "WHERE s.id = :studentId")
    List<Discipline> findDisciplinesByStudentId(@Param("studentId") Long studentId);

}
