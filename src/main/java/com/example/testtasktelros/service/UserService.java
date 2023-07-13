package com.example.testtasktelros.service;

import com.example.testtasktelros.model.Role;
import com.example.testtasktelros.model.User;
import com.example.testtasktelros.repository.RoleRepository;
import com.example.testtasktelros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * The user can view his personal page
     * The user cannot view other users
     * Admin can view any user
     * @param id long
     * @param userDetails UserDetails
     * @return Optional<User>
     */
    public Optional<User> getUserById(long id, UserDetails userDetails) {
        Optional<User> currentUser = userRepository.findByUsernameOrEmail(userDetails.getUsername(),userDetails.getUsername());
        String nameUserById = userRepository.findById(id).get().getUsername();
        String nameCurrentUser = userDetails.getUsername();
        Role userRole = roleRepository.findByName("ROLE_ADMIN").get();
        if(nameCurrentUser.equals(nameUserById) || (currentUser.get().getRoles().contains(userRole))) {
            return userRepository.findById(id);
        } else return Optional.empty();
    }
}
