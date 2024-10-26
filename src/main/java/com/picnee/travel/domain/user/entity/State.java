package com.picnee.travel.domain.user.entity;

public enum State {
    INACTIVE,   // 휴면 계정
    BLOCKED,    // 차단된 사용자
    LOCKED,     // 비밀번호 횟수 초과
    ACTIVE      // 활성화된 사용자
}
