package com.nbu.ejournalgroupproject.security.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Because email is unique, we will use it to find the user
    Optional<User> findByEmail(String email);

}
