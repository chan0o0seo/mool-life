package com.example.backend.auth.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Schema(description = "사용자 고유 식별자(UUID)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(unique = true, nullable = false)
    @Schema(description = "사용자 이메일 (로그인 및 인증에 사용됩니다)",
            example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Column(nullable = false)
    @Schema(description = "사용자 이름 (실명)",
            example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Column(nullable = false)
    @Schema(description = "암호화된 비밀번호 (BCrypt로 저장됨)",
            example = "$2a$10$abcdefg...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Column(nullable = false)
    @Schema(description = "휴대폰 번호 (연락처 정보)",
            example = "010-1234-5678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "계정 상태 (ACTIVE, DORMANT, WITHDRAWN, UNVERIFIED)",
            example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
    private UserStatus status;

    @Column(nullable = false)
    @Schema(description = "사용자 권한 목록",
            example = "ROLE_USER,ROLE_ADMIN", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roles;

    @Column(nullable = false)
    @Schema(description = "OAuth 제공자 (가입 시 OAuth 사용한 경우)",
            example = "google", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String provider;

    @Column
    @Schema(description = "마지막 로그인 시간 (ISO-8601 형식)",
            example = "2025-07-01T12:34:56", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime lastLogin;

    @Column(nullable = false, updatable = false)
    @Schema(description = "계정 생성 시간 (불변)",
            example = "2025-01-01T00:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createdAt;

    @Column
    @Schema(description = "계정 삭제(탈퇴) 시간 (soft delete)",
            example = "2025-06-30T15:20:30", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDateTime deletedAt;
}
