package com.example.usersvc.core.service;

import com.example.usersvc.db.entity.User;
import com.example.usersvc.db.repository.UserRepository;
import com.example.usersvc.exception.NoSuchUserException;
import com.example.usersvc.exception.UserAlreadyExistsException;
import com.example.usersvc.web.dto.UserEditRequest;
import com.example.usersvc.web.dto.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(UserCreateRequest request) {

        Optional<User> optionalUser = userRepository.findByEmailOrPhoneNumber(request.getEmail(),
                request.getPhoneNumber());

        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        return userRepository.save(initializeUser(request));
    }

    public User getById(UUID id) {

        return userRepository.findUserById(id).orElseThrow(NoSuchUserException::new);
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

    public User update(UUID id, UserEditRequest request) {

        User user = getById(id);

        user.setFirstName(request.getFirstName() != null ? request.getFirstName() : user.getFirstName());
        user.setLastName(request.getLastName() != null ? request.getLastName() : user.getLastName());
        user.setEmail(request.getEmail() != null ? request.getEmail() : user.getEmail());
        user.setPhoneNumber(request.getPhoneNumber() != null ? request.getPhoneNumber() : user.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth() != null ? request.getDateOfBirth() : user.getDateOfBirth());
        user.setUpdatedOn(LocalDateTime.now());

        return userRepository.save(user);
    }

    public void delete(UUID id) {

        userRepository.deleteById(id);
    }

    private User initializeUser(UserCreateRequest request) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of()
        );
    }
}