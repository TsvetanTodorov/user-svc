package com.example.usersvc.web.controller;

import com.example.usersvc.core.service.UserService;
import com.example.usersvc.db.entity.User;
import com.example.usersvc.web.dto.UserRequest;
import com.example.usersvc.web.dto.UserResponse;
import com.example.usersvc.web.mapper.DtoMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {

        User user = userService.create(request);

        UserResponse response = DtoMapper.fromUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {

        User user = userService.getById(id);

        UserResponse response = DtoMapper.fromUser(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam @Nullable String term) {

        List<User> users = userService.getAll(term);

        List<UserResponse> responses = users.stream()
                .map(DtoMapper::fromUser)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,
                                                   @RequestBody UserRequest request) {

        User user = userService.update(id, request);

        UserResponse response = DtoMapper.fromUser(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

        userService.delete(id);

        return ResponseEntity.ok().build();
    }

}
