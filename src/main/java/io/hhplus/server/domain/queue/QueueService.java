package io.hhplus.server.domain.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueService {
    private final QueueRepository queueRepository;

    private static final int MAX_QUEUE_SIZE = 50;
    private static final int MAX_ACTIVE_MINUTES = 30;

    public Queue generateToken(long userId) {
        Queue queue = Queue.builder().build();
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

    public List<Queue> findUsersToActivate() {
        int currentEntries = (int) queueRepository.countAllByStatusIs(Queue.Status.ACTIVE);
        int entryLimit = MAX_QUEUE_SIZE - currentEntries;

        long lastActiveUserTokenId = queueRepository.getLastActiveUserTokenId();

        return queueRepository.findAllByStatusIsAndIdGreaterThanOrderByIdAsc(
                Queue.Status.WAITING, lastActiveUserTokenId, PageRequest.of(0, entryLimit));
    }

    public void activateTokens(List<Queue> queueList) {
        for(Queue queue : queueList) {
            queue.activate();
            queueRepository.save(queue);
        }
    }

    public List<Queue> findActiveUsersForMoreThan30Minutes() {
        return queueRepository.findAllByStatusIsAndActivatedAtBefore(Queue.Status.ACTIVE, LocalDateTime.now().minusMinutes(MAX_ACTIVE_MINUTES));
    }

    public void expireTokens(List<Queue> queueList) {
        for(Queue queue : queueList) {
            queue.expire();
            queueRepository.save(queue);
        }
    }
}
