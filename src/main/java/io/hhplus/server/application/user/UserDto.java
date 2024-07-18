package io.hhplus.server.application.user;

import io.hhplus.server.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private long userId;
    private int point;

    public static UserDto toDto(User user) {
        return UserDto.builder()
                      .userId(user.getId())
                      .point(user.getPoint())
                      .build();
    }
}