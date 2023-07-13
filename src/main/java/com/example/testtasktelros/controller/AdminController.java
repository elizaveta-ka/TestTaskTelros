package com.example.testtasktelros.controller;

import com.example.testtasktelros.model.User;
import com.example.testtasktelros.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addNewUser(@RequestBody User user){
        String message = userService.addUser(user);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update-user/{id}")
    public  User updateUser(@PathVariable("id") long id,@RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id){
        String message = userService.deleteUser(id);
        return ResponseEntity.ok(message);
    }

}
