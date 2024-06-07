package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
