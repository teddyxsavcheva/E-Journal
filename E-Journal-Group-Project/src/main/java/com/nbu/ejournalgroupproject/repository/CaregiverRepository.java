package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Long> {
}
