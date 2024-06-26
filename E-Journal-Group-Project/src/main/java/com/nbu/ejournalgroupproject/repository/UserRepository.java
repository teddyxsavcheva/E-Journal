package com.nbu.ejournalgroupproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.nbu.ejournalgroupproject.model.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.authorities auth WHERE u.username= :username")
    User getUserByUsername(String username);

}
