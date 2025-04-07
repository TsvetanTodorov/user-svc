package com.example.usersvc.core.service;

import com.example.usersvc.db.entity.User;
import com.example.usersvc.db.repository.UserRepository;
import com.example.usersvc.exception.NoSuchUserException;
import com.example.usersvc.exception.UserAlreadyExistsException;
import com.example.usersvc.exception.UserUpdateException;
import com.example.usersvc.web.dto.UserRequest;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Literal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(UserRequest request) {

        Optional<User> optionalUser = userRepository.findByEmailOrPhoneNumber(request.getEmail()
                , request.getPhoneNumber());

        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("User with the given email or phone number already exists.");
        }

        return userRepository.save(initializeUser(request));
    }

    private User initializeUser(UserRequest request) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    public User getById(UUID id) {

        return getUserById(id);
    }

    public List<User> getAll(String term) {

        List<User> users;

        if (term != null) {
            users = userRepository.findAllByLastNameContaining(term);
        } else {
            users = userRepository.findAll();
        }

        return users
                .stream()
                .sorted(Comparator
                        .comparing(User::getLastName)
                        .thenComparing(User::getDateOfBirth))
                .toList();
    }

    public User update(UUID id, UserRequest request) {

        if (areAllFieldsNull(request)) {
            throw new UserUpdateException();
        }

        User user = getUserById(id);
        setNewFields(request, user);

        return userRepository.save(user);
    }

    public User delete(UUID id) {

        User user = getUserById(id);

        userRepository.delete(user);

        return user;
    }


    private User getUserById(UUID id) {

        Optional<User> optionalUser = userRepository.findUserById(id);

        return optionalUser.orElseThrow(NoSuchUserException::new);
    }

    private boolean areAllFieldsNull(UserRequest request) {

        return Stream.of(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getDateOfBirth()
        ).allMatch(Objects::isNull);
    }

    private void setNewFields(UserRequest request, User user) {

        user.setFirstName(request.getFirstName() != null ? request.getFirstName() : user.getFirstName());
        user.setLastName(request.getLastName() != null ? request.getLastName() : user.getLastName());
        user.setEmail(request.getEmail() != null ? request.getEmail() : user.getEmail());
        user.setPhoneNumber(request.getPhoneNumber() != null ? request.getPhoneNumber() : user.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth() != null ? request.getDateOfBirth() : user.getDateOfBirth());
        user.setUpdatedOn(LocalDateTime.now());
    }
}