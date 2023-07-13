package com.example.testtasktelros.repository;

import com.example.testtasktelros.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);

}

