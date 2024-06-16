package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.AbsenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceTypeRepository extends JpaRepository<AbsenceType, Long> {
}
