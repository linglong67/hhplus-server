package io.hhplus.server.application.queue;

import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // 토큰 활성화 (스케줄러)
    @Transactional
    public void activateTokens() {
        List<Queue> activateTargets = queueService.findUsersToActivate();
        queueService.activateTokens(activateTargets);
    }

    // 토큰 만료 (스케줄러)
    @Transactional
    public void expireTokens() {
        List<Queue> expireTargets = queueService.findActiveUsersForMoreThan30Minutes();
        queueService.expireTokens(expireTargets);
    }
}
