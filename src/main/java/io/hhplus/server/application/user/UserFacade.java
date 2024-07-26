package io.hhplus.server.application.user;

import io.hhplus.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    // 포인트 조회
    public UserDto getPoint(long userId) {
        return UserDto.toDto(userService.getPoint(userId));
    }

    // 포인트 충전
    public UserDto chargePoint(long userId, int amount) {
        return UserDto.toDto(userService.chargePoint(userId, amount));
    }

    // 포인트 사용
    @Transactional
    public UserDto usePoint(long userId, int amount) {
        return UserDto.toDto(userService.usePoint(userId, amount));
    }
}
