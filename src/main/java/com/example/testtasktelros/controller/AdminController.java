package com.example.testtasktelros.controller;

import com.example.testtasktelros.model.User;
import com.example.testtasktelros.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return adminService.getAllUsers();
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addNewUser(@RequestBody User user){
        String message = adminService.addUser(user);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update-user/{id}")
    public  User updateUser(@PathVariable("id") long id,@RequestBody User user) {
        return adminService.updateUser(id, user);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id){
        String message = adminService.deleteUser(id);
        return ResponseEntity.ok(message);
    }

}
