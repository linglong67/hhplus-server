package io.hhplus.server.application.queue;

import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QueueFacade {
    private final QueueService queueService;

    // 토큰 발급
    @Transactional
    public QueueDto generateToken(long userId) {
        Queue queue = queueService.generateToken(userId);
        long queueOrder = queueService.getQueueOrder(userId, queue.getToken());

        return QueueDto.toDto(queue, queueOrder);
    }

    // 토큰 조회
    @Transactional(readOnly = true)
    public QueueDto getQueueInfo(long userId, String token) {
        Queue queue = queueService.getQueueInfo(userId, token);
        long queueOrder = queueService.getQueueOrder(userId, token);

        return QueueDto.toDto(queue, queueOrder);
    }
}
