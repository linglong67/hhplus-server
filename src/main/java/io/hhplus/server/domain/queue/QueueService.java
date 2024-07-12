package io.hhplus.server.domain.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueService {
    private final QueueRepository queueRepository;

    public Queue generateToken(long userId) {
        Queue queue = new Queue();
        queue.generate(userId);

        return queueRepository.save(queue);
    }

    public long getQueueOrder(long userId, String token) {
        Queue queue = getQueue(userId, token);
        long lastActiveUserTokenId = queueRepository.getLastActiveUserTokenId();

        return queue.getId() - lastActiveUserTokenId;
    }

    public Queue getQueueInfo(long userId, String token) {
        return getQueue(userId, token);
    }

    public void verifyQueue(long userId, String token) {
        Queue queue = getQueue(userId, token);

        if (queue.getStatus() != Queue.Status.ACTIVE) {
            throw new IllegalStateException("활성 토큰이 아님");
        }
    }

    public void expireToken(long userId, String token) {
        Queue queue = getQueue(userId, token);
        queue.expire();
        queueRepository.save(queue);
    }

    private Queue getQueue(long userId, String token) {
        Optional<Queue> queue = queueRepository.getQueue(userId, token);

        if (queue.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 토큰 정보");
        }

        return queue.get();
    }
}
