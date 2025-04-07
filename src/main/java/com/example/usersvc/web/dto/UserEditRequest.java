package com.example.usersvc.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserEditRequest {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    @Size(max = 10, message = "Phone number cannot be longer than 10 digits.")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    @AssertTrue
    public boolean hasAtLeastOneFieldWithValue() {

        return firstName != null || lastName != null || email != null || phoneNumber != null || dateOfBirth != null;
    }
}
