package io.hhplus.server.domain.queue;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
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

    private static final int MAX_ACTIVE_MINUTES = 30;

    public Queue generateToken(long userId) {
        Queue queue = Queue.builder().build();
        queue.generate(userId);

        return queueRepository.save(queue);
    }

    public long getQueueOrder(long userId, String token) {
        Queue queue = getQueue(userId, token);
        long lastActiveUserQueueId = queueRepository.getLastActiveUserQueueId();

        return queue.getId() - lastActiveUserQueueId;
    }

    public Queue getQueueInfo(long userId, String token) {
        return getQueue(userId, token);
    }

    public void verifyQueue(String token) {
        Queue queue = getQueue(token);

        if (queue.getStatus() != Queue.Status.ACTIVE) {
            throw new BusinessException(ErrorCode.QUEUE_TOKEN_NOT_ACTIVE);
        }
    }

    public void expireToken(long userId, String token) {
        Queue queue = getQueue(userId, token);
        queue.expire();
        queueRepository.save(queue);
    }

    private Queue getQueue(String token) {
        Optional<Queue> queue = queueRepository.getQueue(token);

        if (queue.isEmpty()) {
            throw new BusinessException(ErrorCode.QUEUE_TOKEN_NOT_FOUND);
        }

        return queue.get();
    }

    // FIXME: 삭제 고려 중
    private Queue getQueue(long userId, String token) {
        Optional<Queue> queue = queueRepository.getQueue(userId, token);

        if (queue.isEmpty()) {
            throw new BusinessException(ErrorCode.QUEUE_TOKEN_NOT_FOUND);
        }

        return queue.get();
    }

    public List<Queue> findUsersToActivate() {
        int currentEntries = (int) queueRepository.countAllByStatusIs(Queue.Status.ACTIVE);
        long lastActiveUserQueueId = queueRepository.getLastActiveUserQueueId();

        return queueRepository.findAllByStatusIsAndIdGreaterThanOrderByIdAsc(
                Queue.Status.WAITING, lastActiveUserQueueId,
                PageRequest.of(0, Queue.calculateEntryLimit(currentEntries)));
    }

    public void activateTokens(List<Queue> queueList) {
        queueList.forEach(queue -> {
            queue.activate();
            queueRepository.save(queue);
        });
    }

    public List<Queue> findActiveUsersForMoreThan30Minutes() {
        return queueRepository.findAllByStatusIsAndActivatedAtBefore(Queue.Status.ACTIVE, LocalDateTime.now().minusMinutes(MAX_ACTIVE_MINUTES));
    }

    public void expireTokens(List<Queue> queueList) {
        queueList.forEach(queue -> {
            queue.expire();
            queueRepository.save(queue);
        });
    }
}
