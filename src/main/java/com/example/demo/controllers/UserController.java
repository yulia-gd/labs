package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.request.UserUpdateRequest;
import com.example.demo.responce.ApiResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.apache.tomcat.websocket.Constants.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.status(FOUND).body(new ApiResponse("User found", user));
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllBooks() {
        return ResponseEntity.status(FOUND).body(new ApiResponse("All users found", userService.getAllUsers()));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody User user) {
        User addedUser = userService.saveUser(user);
        return ResponseEntity.status(CREATED).body(new ApiResponse("User added. Location: "+ "http://localhost:8080/api/v1/users/user/"+addedUser.getId(), addedUser));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(new ApiResponse("User updated. Location: "+ "http://localhost:8080/api/v1/users/user/"+updatedUser.getId(), updatedUser));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("User deleted", null));
    }

    @PutMapping("/borrow-book/{userId}/{bookId}")
    public ResponseEntity<ApiResponse> borrowBook(@PathVariable Long userId, @PathVariable Long bookId) {
        User user = userService.borrowBook(userId, bookId);
        return ResponseEntity.ok(new ApiResponse("Book borrowed successfully", user));
    }


    @PutMapping("/return-book/{userId}/{bookId}")
    public ResponseEntity<ApiResponse> returnBook(@PathVariable Long userId, @PathVariable Long bookId) {
        User user = userService.returnBook(userId, bookId);
        return ResponseEntity.ok(new ApiResponse("Book returned successfully", user));
    }
}
