package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.GradeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeTypeRepository extends JpaRepository<GradeType, Long> {
}
