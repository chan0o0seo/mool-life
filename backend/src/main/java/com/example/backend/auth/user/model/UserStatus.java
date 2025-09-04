package com.example.backend.auth.user.model;

public enum UserStatus {
    ACTIVE,       // 정상 사용 가능한 상태
    DORMANT,      // 휴면 상태, 로그인 제한
    DEACTIVATE,    // 탈퇴 처리된 계정
    UNVERIFIED    // 이메일 인증 대기 상태
}
