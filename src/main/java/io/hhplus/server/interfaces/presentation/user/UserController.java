package io.hhplus.server.interfaces.presentation.user;

import io.hhplus.server.application.user.UserFacade;
import io.hhplus.server.interfaces.presentation.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "사용자", description = "사용자 포인트 관리를 위한 API")
public class UserController {
    private final UserFacade userFacade;

    @GetMapping("{userId}/points")
    @Operation(summary = "포인트 조회", description = "사용자의 현재 포인트를 조회하는 API")
    public UserResponse getPoint(@PathVariable("userId") long userId) {
        return UserResponse.toResponse(userFacade.getPoint(userId));
    }

    @PatchMapping("{userId}/points/charge")
    @Operation(summary = "포인트 충전", description = "사용자의 포인트를 충전하는 API")
    public UserResponse chargePoint(@PathVariable("userId") long userId, @RequestBody int amount) {
        return UserResponse.toResponse(userFacade.chargePoint(userId, amount));
    }

    @PatchMapping("{userId}/points/use")
    @Operation(summary = "포인트 사용", description = "사용자의 포인트를 사용하는 API")
    public UserResponse usePoint(@PathVariable("userId") long userId, @RequestBody int amount) {
        return UserResponse.toResponse(userFacade.usePoint(userId, amount));
    }
}