package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.StudentCurriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCurriculumRepository extends JpaRepository<StudentCurriculum, Long> {

}
