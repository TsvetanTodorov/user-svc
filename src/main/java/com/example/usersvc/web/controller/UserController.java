package com.example.usersvc.web.controller;

import com.example.usersvc.core.service.UserService;
import com.example.usersvc.db.entity.User;
import com.example.usersvc.web.dto.UserEditRequest;
import com.example.usersvc.web.dto.UserRegisterRequest;
import com.example.usersvc.web.dto.UserResponse;
import com.example.usersvc.web.mapper.DtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "Operations related to users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided registration details and returns the created user.")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRegisterRequest request) {

        User user = userService.create(request);

        UserResponse response = DtoMapper.fromUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve user by ID", description = "Fetches the user details for the specified user ID.")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {

        User user = userService.getById(id);

        UserResponse response = DtoMapper.fromUser(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    @Operation(summary = "Retrieve a list of users", description = "Fetches a list of users, optionally filtered by the provided search term.")
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(required = false) String term) {

        List<User> users = userService.getAll(term);

        List<UserResponse> responses = users.stream()
                .map(DtoMapper::fromUser)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update user details", description = "Updates the details of an existing user identified by the specified user ID.")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,
                                                   @RequestBody @Valid UserEditRequest request) {

        User user = userService.update(id, request);

        UserResponse response = DtoMapper.fromUser(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes the user identified by the specified user ID.")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

        userService.delete(id);

        return ResponseEntity.ok().build();
    }
}
