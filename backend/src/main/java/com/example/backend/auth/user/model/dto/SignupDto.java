package com.example.backend.auth.user.model.dto;

import lombok.Getter;

@Getter
public class SignupDto {

    String email;

    String password;

    String name;

    String phoneNumber;

    String roles;
}
