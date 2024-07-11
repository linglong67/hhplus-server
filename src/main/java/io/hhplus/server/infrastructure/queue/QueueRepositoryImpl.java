package io.hhplus.server.infrastructure.queue;

import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {
    private final QueueJpaRepository queueRepository;

    @Override
    public Optional<Queue> getQueue(long userId, String token) {
        return Optional.empty();
    }

    @Override
    public Queue save(Queue queue) {
        return null;
    }

    @Override
    public long getLastActiveUserTokenId() {
        return 0;
    }
}