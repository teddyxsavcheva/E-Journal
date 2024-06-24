package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Headmaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadmasterRepository extends JpaRepository<Headmaster, Long> {
    Headmaster findBySchoolId(Long schoolId);
}
