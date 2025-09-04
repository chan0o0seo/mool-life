package com.example.backend.auth.user.service;

import com.example.backend.auth.security.PasswordConfig;
import com.example.backend.auth.user.model.UserStatus;
import com.example.backend.auth.user.model.Users;
import com.example.backend.auth.user.model.dto.SignupDto;
import com.example.backend.auth.user.model.dto.UserInfoDto;
import com.example.backend.auth.user.repository.UserRepository;
import com.example.backend.exception.CustomException;
import com.example.backend.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordConfig passwordConfig;

    @Transactional
    public void signUp(SignupDto signupDto) {

        // email 중복 체크
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATED);
        }
        PasswordEncoder passwordEncoder = passwordConfig.passwordEncoder();
        String rawPassword = passwordEncoder.encode(signupDto.getPassword());

        // 새로 가입할 user 객체 생성
        Users newUser = Users.builder()
                .name(signupDto.getName())
                .password(rawPassword)
                .email(signupDto.getEmail())
                .phoneNumber(signupDto.getPhoneNumber())
                .provider("local")
                .roles(signupDto.getRoles())
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        // db 저장
        userRepository.save(newUser);
    }

    @Transactional
    public UserInfoDto getUserInfo(String email) {
        // 유저 조회
        Users user = findUserByEmail(email);

        // 유저 정보 dto 생성
        UserInfoDto userInfo = UserInfoDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles())
                .status(user.getStatus())
                .lastLogin(user.getLastLogin())
                .build();

        // 마지막 로그인 시간 저장
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return userInfo;
    }

    @Transactional
    public void softDeleteUser(String email) {
        // 유저 조회
        Users user = findUserByEmail(email);

        // soft delete 후 저장
        user.setStatus(UserStatus.DEACTIVATE);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);

    }

    // 유틸 함수

    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));
    }
}
