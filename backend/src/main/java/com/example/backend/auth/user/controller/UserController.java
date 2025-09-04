package com.example.backend.auth.user.controller;

import com.example.backend.auth.security.JwtUtil;
import com.example.backend.auth.user.model.dto.SignupDto;
import com.example.backend.auth.user.model.dto.UserInfoDto;
import com.example.backend.auth.user.service.CookieService;
import com.example.backend.auth.user.service.UserService;
import com.example.backend.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/user")
public class UserController {


    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final CookieService cookieService;
    private final AuthenticationManager authManager;

    @Value("${jwt.access-name}")
    private String accessName;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<String>> signUp(
            @RequestBody SignupDto signupDto
    ) {

        // 가입
        userService.signUp(signupDto);

        return ResponseEntity.ok()
                .body(BaseResponse.success("signup success"));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<UserInfoDto>> login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        // 인증
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // accessToken 발급
        String accessToken = jwtUtil.generateToken(email);
        ResponseCookie accessCookie = cookieService.buildAccessCookie(accessToken);

        // 쿠키 설정 및 유저 정보 반환
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .body(BaseResponse.success(userService.getUserInfo(email)));
    }

    @GetMapping("/logout")
    public ResponseEntity<BaseResponse<String>> logout(
            @AuthenticationPrincipal User user
    ) {
        // 쿠키 삭제
        ResponseCookie accessCookie = cookieService.deleteCookie(accessName);

        // 쿠키 설정
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .body(BaseResponse.success("logout success"));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<String>> delete(
            @AuthenticationPrincipal User user
    ) {
        // 유저 정보 변경
        userService.softDeleteUser(user.getUsername());

        // 쿠키 삭제
        ResponseCookie accessCookie = cookieService.deleteCookie(accessName);

        // 쿠키 설정
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .body(BaseResponse.success("logout success"));
    }

}
