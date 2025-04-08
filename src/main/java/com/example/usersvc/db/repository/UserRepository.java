package com.example.usersvc.db.repository;

import com.example.usersvc.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<User> findUserById(UUID id);

    List<User> findAllByLastNameContaining(String lastName);

    Optional<User> findByEmail(String email);
}
