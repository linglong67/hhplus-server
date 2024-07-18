package io.hhplus.server.interfaces.presentation.user.dto;

import io.hhplus.server.application.user.PointDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointResponse {
    private long userId;
    private int point;

    public static PointResponse toResponse(PointDto dto) {
        return PointResponse.builder()
                            .userId(dto.getUserId())
                            .point(dto.getPoint())
                            .build();
    }
}
