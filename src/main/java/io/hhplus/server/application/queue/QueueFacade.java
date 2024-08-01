package io.hhplus.server.application.queue;

import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueFacade {
    private final QueueService queueService;

    // 대기열에 추가
    public QueueDto addQueue(long userId) {
        Queue queue = queueService.addQueue(userId);
        long queueOrder = queueService.getQueueOrder(queue.getToken());

        return QueueDto.toDto(queue, queueOrder);
    }

    // 대기열 순서 조회
    public QueueDto getQueueOrder(String token) {
        long queueOrder = queueService.getQueueOrder(token);

        return QueueDto.toDto(Queue.builder().token(token).build(), queueOrder);
    }

    // 토큰 검증
    public void verifyToken(String token) {
        queueService.verifyToken(token);
    }

    // 토큰 활성화
    public void activateTokens() {
        queueService.activateTokens();
    }

    // 토큰 만료
    public void expireTokens() {
        queueService.expireTokens();
    }

}
