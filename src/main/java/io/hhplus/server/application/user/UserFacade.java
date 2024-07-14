package io.hhplus.server.application.user;

import io.hhplus.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    // 포인트 조회
    public PointDto getPoint(long userId) {
        return PointDto.toDto(userService.getPoint(userId));
    }

    // 포인트 충전
    public PointDto chargePoint(long userId, int amount) {
        return PointDto.toDto(userService.chargePoint(userId, amount));
    }

    // 포인트 사용
    public PointDto usePoint(long userId, int amount) {
        return PointDto.toDto(userService.usePoint(userId, amount));
    }
}
