package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.DisciplineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineTypeRepository extends JpaRepository<DisciplineType, Long> {

}
