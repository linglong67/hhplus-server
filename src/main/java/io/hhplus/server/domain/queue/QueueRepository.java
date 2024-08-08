package io.hhplus.server.domain.queue;

public interface QueueRepository {
    void addQueue(String token);

    Long getQueueOrder(String token);

    Boolean verifyToken(String tokenWithTime);

    void expireToken(String tokenWithTime);

    void activateTokens();

    void expireTokens();
}