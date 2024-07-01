package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    @Query("SELECT COUNT(a) FROM Absence a JOIN a.absenceStatus ast  WHERE a.student.id = :studentId AND a.discipline.id = :disciplineId AND ast.absenceStatusEnum = 'EXCUSED'")
    Long excusedAbsencesCountByStudentAndDiscipline(Long studentId, Long disciplineId);
    @Query("SELECT COUNT(a) FROM Absence a JOIN a.absenceStatus ast  WHERE a.student.id = :studentId AND a.discipline.id = :disciplineId AND ast.absenceStatusEnum = 'NOT_EXCUSED'")
    Long notExcusedAbsencesCountByStudentAndDiscipline(Long studentId, Long disciplineId);

    List<Absence> getAllByDisciplineIdAndStudentId(Long disciplineId, Long studentId);

}
