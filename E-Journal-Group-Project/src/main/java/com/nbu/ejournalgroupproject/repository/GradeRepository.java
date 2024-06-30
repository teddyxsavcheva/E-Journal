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

    @Query("SELECT t.name, d.name, " +
            "       CASE " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 3.5 THEN 'A' " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 2.5 THEN 'B' " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 1.5 THEN 'C' " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 0.5 THEN 'D' " +
            "           ELSE 'F' " +
            "       END AS average_letter_grade " +
            "FROM Grade g " +
            "JOIN g.gradeType gt " +
            "JOIN g.discipline d " +
            "JOIN StudentCurriculumHasTeacherAndDiscipline tads ON g.discipline.id = tads.discipline.id " +
            "JOIN tads.teacher t " +
            "JOIN g.student s " +
            "JOIN s.schoolClass sc " +
            "JOIN sc.school sch " +
            "JOIN Headmaster h ON sch.id = h.school.id " +
            "WHERE h.id = :headmasterId " +
            "GROUP BY t.name, d.name")
    List<String> findAvgGradeForTeacherByDiscipline(@Param("headmasterId") Long headmasterId);

    @Query("SELECT d.name, " +
            "       CASE " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 3.5 THEN 'A' " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 2.5 THEN 'B' " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 1.5 THEN 'C' " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 0.5 THEN 'D' " +
            "           ELSE 'F' " +
            "       END AS average_letter_grade " +
            "FROM Grade g " +
            "JOIN g.gradeType gt " +
            "JOIN g.discipline d " +
            "JOIN g.student s " +
            "JOIN s.schoolClass sc " +
            "JOIN sc.school sch " +
            "JOIN Headmaster h ON sch.id = h.school.id " +
            "WHERE h.id = :headmasterId " +
            "GROUP BY d.name")
    List<String> findAvgGradeForDiscipline(@Param("headmasterId") Long headmasterId);

    @Query("SELECT sch.name, " +
            "       CASE " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 3.5 THEN 'A' " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 2.5 THEN 'B' " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 1.5 THEN 'C' " +
            "           WHEN AVG(CASE " +
            "                       WHEN gt.gradeTypeEnum = 'A' THEN 4.0 " +
            "                       WHEN gt.gradeTypeEnum = 'B' THEN 3.0 " +
            "                       WHEN gt.gradeTypeEnum = 'C' THEN 2.0 " +
            "                       WHEN gt.gradeTypeEnum = 'D' THEN 1.0 " +
            "                       WHEN gt.gradeTypeEnum = 'F' THEN 0.0 " +
            "                       ELSE 0.0 " +
            "                   END) >= 0.5 THEN 'D' " +
            "           ELSE 'F' " +
            "       END AS average_letter_grade " +
            "FROM Grade g " +
            "JOIN g.gradeType gt " +
            "JOIN g.student s " +
            "JOIN s.schoolClass sc " +
            "JOIN sc.school sch " +
            "JOIN Headmaster h ON sch.id = h.school.id " +
            "WHERE h.id = :headmasterId " +
            "GROUP BY sch.name")
    List<String> findAvgGradeForSchool(@Param("headmasterId") Long headmasterId);
}
