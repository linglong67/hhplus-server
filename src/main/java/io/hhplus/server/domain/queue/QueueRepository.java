package io.hhplus.server.domain.queue;

import java.util.Optional;

public interface QueueRepository {
    Optional<Queue> getQueue(long userId, String token);

    Queue save(Queue queue);

    long getLastActiveUserTokenId();
}