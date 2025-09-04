package com.example.backend.auth.user.model.dto;

import com.example.backend.auth.user.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private String email;

    private String name;

    private String phoneNumber;

    private String roles;

    private UserStatus status;

    private LocalDateTime lastLogin;
}
