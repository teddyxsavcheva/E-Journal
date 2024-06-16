package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.AbsenceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceStatusRepository extends JpaRepository<AbsenceStatus, Long> {
}
