package com.example.usersvc.web.mapper;

import com.example.usersvc.db.entity.User;
import com.example.usersvc.web.dto.UserResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {

    public static UserResponse fromUser(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
//                .role(user.getRole())
                .build();
    }
}
