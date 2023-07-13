package com.example.testtasktelros.service;

import com.example.testtasktelros.exception.APIException;
import com.example.testtasktelros.model.Role;
import com.example.testtasktelros.model.User;
import com.example.testtasktelros.repository.RoleRepository;
import com.example.testtasktelros.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public AdminService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * Admin can get all users
     * @return List<User>
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Admin can add new user
     * @param user User object
     * @return String
     */
    public String addUser(User user){

        if(userRepository.existsByUsername(user.getUsername())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is already exists!");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already exists!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "User registered successfully!";
    }

    /**
     * Admin can update user
     * @param id long
     * @param user User object
     * @return User
     */
    public User updateUser(long id, User user) {
        User userNew = userRepository.findById(id).get();
        userNew.setFullName(user.getFullName());
        userNew.setUsername(user.getUsername());
        userNew.setSurname(user.getSurname());
        userNew.setBirthday(user.getBirthday());
        userNew.setPatronymic(user.getPatronymic());
        userNew.setEmail(user.getEmail());
        userNew.setImage(user.getImage());
        userNew.setNumberPhone(user.getNumberPhone());
        userNew.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(userNew);
    }

    /**
     * Admin can delete user
     * @param id long
     * @return String
     */
    public String deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        user.get().setRoles(null);
        userRepository.delete(userRepository.findById(id).get());
        return "User deleted";
    }
}
