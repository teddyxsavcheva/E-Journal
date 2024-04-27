package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

}
