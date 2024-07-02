package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT CASE WHEN COUNT(sc) <= 1 THEN TRUE ELSE FALSE END FROM Student s JOIN s.caregivers sc WHERE s.id = :studentId")
    Boolean hasLessThenTwoCaregivers(@Param("studentId") Long studentId);

    List<Student> getStudentsBySchoolClassId(Long id);
}
