package com.example.testtasktelros.service;

import com.example.testtasktelros.model.Role;
import com.example.testtasktelros.model.User;
import com.example.testtasktelros.repository.RoleRepository;
import com.example.testtasktelros.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminServiceTest {

    @Test
    public void testGetAllUsers() {
        Set<Role> roles = new HashSet<>();
        Role userRole = new Role(2L,"ROLE_USER");
        roles.add(userRole);
        // Create test users
        User user1 = new User(7,"Jonson","jack","Jack","David", "13.02.1996","jack@gmail.com","2222","img","jack",roles);
        User user2 = new User(8,"Ban","jone","Jone","David", "13.02.1996","jone@gmail.com","11111","img","jone",roles);
        List<User> users = Arrays.asList(user1, user2);

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        AdminService adminService = new AdminService(userRepository);

        List<User> result = adminService.getAllUsers();

        // Check result
        assertEquals(users, result);
    }

    @Test
    public void testUpdateUserPasswordEncryption() {
        // Create test user
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("john");
        existingUser.setPassword("password123");

        // Create updating user
        User updatedUser = new User();
        updatedUser.setPassword("newpassword456");

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(existingUser)).thenReturn(existingUser);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AdminService userService = new AdminService(userRepository, passwordEncoder);

        // updateUser()
        User result = userService.updateUser(1, updatedUser);

        //check result
        assertTrue(passwordEncoder.matches(updatedUser.getPassword(), result.getPassword()));
        //Checking that other user fields have not been changed
        assertEquals(existingUser.getUsername(), result.getUsername());
    }

    @Test
    public void testDeleteUser() {
        // Create test user
        User user = new User();
        user.setId(1L);

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        AdminService adminService = new AdminService(userRepository);

        // deleteUser()
        String result = adminService.deleteUser(1L);

        // check result
        assertEquals("User deleted", result);
        // Checking that the findById() method was called to remove the user
        Mockito.verify(userRepository, Mockito.times(2)).findById(1L);
        // Checking if delete() method was called to delete a user
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    public void testDeleteUserNotFound() {

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        AdminService adminService = new AdminService(userRepository);

        // deleteUser()
        String result = adminService.deleteUser(1L);
        // check result
        assertNull(result);
        // Checking that the findById() method was called to find the user
        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
        //Checking if delete() method has not been called
        Mockito.verify(userRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    public void testAddUser() {
        //create test user
        User user = new User();
        user.setUsername("john");
        user.setEmail("john@example.com");
        user.setPassword("password123");

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AdminService adminService = new AdminService(userRepository, passwordEncoder, roleRepository);

        // addUser()
        String result = adminService.addUser(user);

        // check result
        assertEquals("User registered successfully!", result);
        // Checking that the save() method was called to save the user
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testAddUserUsernameExists() {
        // create test user
        User user = new User();
        user.setUsername("john");


        UserRepository userRepository = Mockito.mock(UserRepository.class);
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        AdminService adminService = new AdminService(userRepository, passwordEncoder, roleRepository);

        // Call to addUser() method, expected to throw an APIException
        adminService.addUser(user);
    }

    @Test
    public void testAddUserEmailExists() {
        //create test user
        User user = new User();
        user.setEmail("john@example.com");

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        AdminService adminService = new AdminService(userRepository, passwordEncoder, roleRepository);

        // Call to addUser() method, expected to throw an APIException
        adminService.addUser(user);
    }
}
