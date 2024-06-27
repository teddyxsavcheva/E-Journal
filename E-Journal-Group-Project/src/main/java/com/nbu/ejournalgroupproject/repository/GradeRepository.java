package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Grade;
import com.nbu.ejournalgroupproject.model.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    @Query("SELECT gt.gradeTypeEnum FROM Grade g JOIN g.gradeType gt WHERE g.student.id = :studentId AND g.discipline.id = :disciplineId")
    List<String> findGradeTypesByStudentAndDiscipline(@Param("studentId") Long studentId, @Param("disciplineId") Long disciplineId);

}
