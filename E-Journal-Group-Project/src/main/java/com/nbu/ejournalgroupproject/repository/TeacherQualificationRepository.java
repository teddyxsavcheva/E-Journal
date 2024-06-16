package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.TeacherQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherQualificationRepository extends JpaRepository<TeacherQualification, Long> {

}