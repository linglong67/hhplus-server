package io.hhplus.server.domain.queue;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {
    private final QueueRepository queueRepository;

    public Queue addQueue(long userId) {
        Queue queue = Queue.builder().build();
        queue.generate(userId);
        queueRepository.addQueue(queue.getToken());

        return queue;
    }

    public long getQueueOrder(String token) {
        Long queueOrder = queueRepository.getQueueOrder(token);
        if (queueOrder == null) {
            throw new BusinessException(ErrorCode.QUEUE_TOKEN_NOT_FOUND);
        }

        return queueOrder;
    }

    public void verifyToken(String tokenWithTime) {
        if (!Boolean.TRUE.equals(queueRepository.verifyToken(tokenWithTime))) {
            throw new BusinessException(ErrorCode.QUEUE_TOKEN_NOT_ACTIVE);
        }
    }

    public void expireToken(String tokenWithTime) {
        queueRepository.expireToken(tokenWithTime);
    }

    public void activateTokens() {
        queueRepository.activateTokens();
    }

    public void expireTokens() {
        queueRepository.expireTokens();
    }
}
