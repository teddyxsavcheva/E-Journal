package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findAllBySchoolId(long id);

    @Query("SELECT DISTINCT sc " +
            "FROM Teacher t " +
            "JOIN StudentCurriculumHasTeacherAndDiscipline schtd ON t.id = schtd.teacher.id " +
            "JOIN StudentCurriculum scurr ON schtd.studentCurriculum.id = scurr.id " +
            "JOIN SchoolClass sc ON scurr.schoolClass.id = sc.id " +
            "WHERE t.id = :teacherId")
    List<SchoolClass> findDistinctClassesByTeacherId(Long teacherId);
}
