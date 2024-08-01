package io.hhplus.server.application.queue;

import io.hhplus.server.domain.queue.Queue;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueueDto {
    private long userId;
    private String token;
    private String status;
    private long queueOrder;

    public static QueueDto toDto(Queue queue, long queueOrder) {
        return QueueDto.builder()
                       .token(queue.getToken())
                       .queueOrder(queueOrder)
                       .build();
    }
}
