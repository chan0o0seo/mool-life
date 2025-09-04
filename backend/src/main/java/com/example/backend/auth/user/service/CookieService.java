package com.example.backend.auth.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CookieService {

    @Value("${jwt.access-name}")
    private String accessName;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    public ResponseCookie deleteCookie(String name) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(name, "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict");

        return builder.build();
    }

    public ResponseCookie buildAccessCookie(String token) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(accessName, token)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofMillis(accessExpiration))
                .sameSite("Strict");

        return builder.build();
    }

}