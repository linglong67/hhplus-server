package io.hhplus.server.infrastructure.queue;

import io.hhplus.server.domain.queue.Queue;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface QueueJpaRepository extends JpaRepository<QueueEntity, Long> {
}
