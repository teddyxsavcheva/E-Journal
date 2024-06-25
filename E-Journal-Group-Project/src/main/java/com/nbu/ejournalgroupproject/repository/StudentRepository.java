package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT CASE WHEN COUNT(cs) <= 1 THEN true ELSE false END FROM Student s JOIN s.caregivers cs WHERE s.id = :studentId GROUP BY s.id")
    boolean hasLessThenTwoCaregivers(@Param("studentId") Long studentId);
}
