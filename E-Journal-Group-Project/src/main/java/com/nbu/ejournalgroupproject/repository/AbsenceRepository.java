package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}
