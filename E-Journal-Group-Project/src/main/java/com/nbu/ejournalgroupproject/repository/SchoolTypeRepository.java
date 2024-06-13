package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.SchoolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolTypeRepository extends JpaRepository<SchoolType, Long> {

}
