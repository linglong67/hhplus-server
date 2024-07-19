package io.hhplus.server.domain.user;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private Long id;
    private String name;
    private Integer point;

    public void chargePoint(int amount) {
        if (amount <= 0) {
            throw new BusinessException(ErrorCode.USER_POINT_INVALID_VALUE);
        }

        this.point += amount;
    }

    public void usePoint(int amount) {
        if (amount <= 0) {
            throw new BusinessException(ErrorCode.USER_POINT_INVALID_VALUE);
        }

        if (this.point < amount) {
            throw new BusinessException(ErrorCode.USER_POINT_NOT_ENOUGH);
        }

        this.point -= amount;
    }
}
