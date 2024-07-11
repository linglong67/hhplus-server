package io.hhplus.server.application.user;

import io.hhplus.server.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointDto {
    private long userId;
    private int point;

    public static PointDto toDto(User user) {
        return PointDto.builder()
                       .userId(user.getId())
                       .point(user.getPoint())
                       .build();
    }
}