package com.example.usersvc.web.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ErrorResponse {

    private String message;

    private String time;

}
