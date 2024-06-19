package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT COUNT(t) > 0 " +
            "FROM Teacher t " +
            "JOIN t.teacherQualifications tq " +
            "JOIN tq.disciplines d " +
            "WHERE t.id = :teacherId AND d.id = :disciplineId")
    boolean isTeacherQualifiedForDiscipline(@Param("teacherId") Long teacherId, @Param("disciplineId") Long disciplineId);
}