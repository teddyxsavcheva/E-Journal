package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
    @Query("SELECT c FROM Caregiver c JOIN c.students s WHERE s.id = :studentId")
    List<Caregiver> findCaregiversByStudentId(@Param("studentId") Long studentId);
}
