package io.hhplus.server.infrastructure.queue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueJpaRepository extends JpaRepository<QueueEntity, Long> {
}
