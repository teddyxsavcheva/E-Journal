package com.nbu.ejournalgroupproject.repository;

import com.nbu.ejournalgroupproject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
