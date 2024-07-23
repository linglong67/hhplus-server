package io.hhplus.server.interfaces.presentation.user.dto;

import io.hhplus.server.application.user.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private long userId;
    private int point;

    public static UserResponse toResponse(UserDto dto) {
        return UserResponse.builder()
                           .userId(dto.getUserId())
                           .point(dto.getPoint())
                           .build();
    }
}
