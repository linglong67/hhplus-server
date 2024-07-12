package io.hhplus.server.domain.queue;

import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueueRepository {
    Optional<Queue> getQueue(long userId, String token);

    Queue save(Queue queue);

    long getLastActiveUserTokenId();

    List<Queue> findAllByStatusIsAndActivatedAtBefore(String status, LocalDateTime validationTime);

    long countAllByStatusIs(String name);

    List<Queue> findAllByStatusIsAndIdGreaterThanOrderByIdAsc(String status, long lastActiveUserTokenId, PageRequest of);
}