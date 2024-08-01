package io.hhplus.server.infrastructure.queue;

import io.hhplus.server.domain.queue.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {
    private final QueueRedisRepository queueRepository;

    @Override
    public void addQueue(String token) {
        queueRepository.addQueue(token);
    }

    @Override
    public Long getQueueOrder(String token) {
        return queueRepository.getQueueOrder(token);
    }

    @Override
    public Boolean verifyToken(String tokenWithTime) {
        return queueRepository.verifyToken(tokenWithTime);
    }

    @Override
    public void expireToken(String tokenWithTime) {
        queueRepository.expireToken(tokenWithTime);
    }

    @Override
    public void activateTokens() {
        queueRepository.activateTokens();
    }

    @Override
    public void expireTokens() {
        queueRepository.expireTokens();
    }
}