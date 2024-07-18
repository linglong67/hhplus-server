package io.hhplus.server.domain.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 공통
    INTERNAL_SERVER_ERROR("E001", "Internal Server Error"),
    ;

    private final String code;
    private final String message;
}
