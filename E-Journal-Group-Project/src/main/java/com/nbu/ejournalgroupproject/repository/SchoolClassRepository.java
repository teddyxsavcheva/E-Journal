package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findAllBySchoolId(long id);
}
