package com.nbu.ejournalgroupproject.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Because email is unique, we will use it to find the user
    Optional<User> findByEmail(String email);
}
