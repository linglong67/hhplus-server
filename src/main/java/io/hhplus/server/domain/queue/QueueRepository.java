package io.hhplus.server.domain.queue;

import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueueRepository {
    Optional<Queue> getQueue(String token);

    Optional<Queue> getQueue(long userId, String token);

    Queue save(Queue queue);

    long getLastActiveUserQueueId();

    List<Queue> findAllByStatusIsAndActivatedAtBefore(Queue.Status status, LocalDateTime validationTime);

    long countAllByStatusIs(Queue.Status status);

    List<Queue> findAllByStatusIsAndIdGreaterThanOrderByIdAsc(Queue.Status status, long lastActiveUserQueueId, PageRequest of);

    void deleteAll();

    Optional<Queue> findById(Long id);
}