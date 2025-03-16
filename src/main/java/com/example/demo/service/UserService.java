package com.example.demo.service;

import com.example.demo.entities.User;
import com.example.demo.request.UserUpdateRequest;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    User saveUser(User user);
    void deleteUser(Long id);
    User updateUser(Long id, UserUpdateRequest user);
    User borrowBook(Long userId, Long bookId);
    User returnBook(Long userId, Long bookId);
}
