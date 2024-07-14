package io.hhplus.server.infrastructure.queue;

import io.hhplus.server.domain.queue.Queue;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueueJpaRepository extends JpaRepository<QueueEntity, Long> {
    List<Queue> findAllByStatusIsAndActivatedAtBefore(Queue.Status status, LocalDateTime validationTime);

    long countAllByStatusIs(Queue.Status status);

    List<Queue> findAllByStatusIsAndIdGreaterThanOrderByIdAsc(Queue.Status status, long id, PageRequest pageRequest);

    Optional<Queue> findByUserIdAndToken(long userId, String token);
}