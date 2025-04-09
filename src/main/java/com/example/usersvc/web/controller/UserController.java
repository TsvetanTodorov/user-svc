package com.example.usersvc.web.controller;

import com.example.usersvc.constant.OperationDescriptions;
import com.example.usersvc.core.service.UserService;
import com.example.usersvc.db.entity.User;
import com.example.usersvc.web.dto.UserEditRequest;
import com.example.usersvc.web.dto.UserCreateRequest;
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

import static com.example.usersvc.constant.OperationDescriptions.*;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = USER_MANAGEMENT, description = USER_MANAGEMENT_DESCRIPTION)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @Operation(summary = OperationDescriptions.CREATE_USER, description = CREATE_USER_DESCRIPTION)
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) {

        User user = userService.create(request);

        UserResponse response = DtoMapper.fromUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = OperationDescriptions.GET_USER, description = GET_USER_DESCRIPTION)
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {

        User user = userService.getById(id);

        UserResponse response = DtoMapper.fromUser(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    @Operation(summary = OperationDescriptions.GET_USERS, description = GET_USERS_DESCRIPTION)
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
    @Operation(summary = OperationDescriptions.UPDATE_USER, description = UPDATE_USER_DESCRIPTION)
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id,
                                                   @RequestBody @Valid UserEditRequest request) {

        User user = userService.update(id, request);

        UserResponse response = DtoMapper.fromUser(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = OperationDescriptions.DELETE_USER, description = DELETE_USER_DESCRIPTION)
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {

        userService.delete(id);

        return ResponseEntity.ok().build();
    }
}
