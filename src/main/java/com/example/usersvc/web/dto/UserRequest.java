package com.example.usersvc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;

@Data
@Builder
public class UserRequest {

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Size(max = 10, message = "Phone number cannot be longer than 10 digits.")
    private String phoneNumber;

    @NotNull
    private LocalDate dateOfBirth;

}
