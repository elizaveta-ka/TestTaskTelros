package com.example.testtasktelros.service;

import com.example.testtasktelros.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(long id, UserDetails userDetails);
}
