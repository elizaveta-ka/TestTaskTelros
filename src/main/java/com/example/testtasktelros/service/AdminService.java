package com.example.testtasktelros.service;

import com.example.testtasktelros.model.User;

import java.util.List;


public interface AdminService {

    List<User> getAllUsers();

    String addUser(User user);

    User updateUser(long id, User user);

    String deleteUser(long id);
}
