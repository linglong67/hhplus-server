package io.hhplus.server.interfaces.presentation.queue.dto;

import io.hhplus.server.application.queue.QueueDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueueResponse {
    private long userId;
    private String token;
    private String status;
    private long queueOrder;

    public static QueueResponse toResponse(QueueDto dto) {
        return QueueResponse.builder()
                            .userId(dto.getUserId())
                            .token(dto.getToken())
                            .status(dto.getStatus())
                            .queueOrder(dto.getQueueOrder())
                            .build();
    }
}
