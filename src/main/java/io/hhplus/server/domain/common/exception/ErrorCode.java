package io.hhplus.server.domain.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 공통
    INTERNAL_SERVER_ERROR("E001", "Internal Server Error"),
    // 콘서트
    CONCERT_SEAT_NOT_FOUND("EC001", "존재하지 않는 좌석"),
    CONCERT_SEAT_ALREADY_OCCUPIED("EC002", "이미 선택된 좌석"),
    CONCERT_SEAT_NOT_AVAILABLE("EC003", "존재하지 않거나 이미 선택된 좌석이 포함됨"),
    // 대기열
    QUEUE_TOKEN_NOT_FOUND("EQ001", "유효하지 않은 토큰 정보"),
    QUEUE_TOKEN_NOT_ACTIVE("EQ002", "활성 토큰이 아님"),
    // 예약
    RESERVATION_NOT_FOUND("ER001", "예약 정보를 찾을 수 없음"),
    // 사용자
    USER_NOT_FOUND("EU001", "존재하지 않는 사용자"),
    USER_POINT_NOT_ENOUGH("EU002", "잔액 부족"),
    USER_POINT_INVALID_VALUE("EU003", "충전/사용 포인트 < 0"),
    ;

    private final String code;
    private final String message;
}
