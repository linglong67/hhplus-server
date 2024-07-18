package io.hhplus.server.domain.queue;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Queue {
    private Long id;
    private Long userId;
    private String token;
    private Status status;
    private LocalDateTime activatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Status {
        WAITING,
        ACTIVE,
        EXPIRED
    }

    private static final int MAX_QUEUE_SIZE = 50;

    public void generate(long userId) {
        this.userId = userId;
        this.token = UUID.randomUUID().toString();
        this.status = Status.WAITING;
    }

    public void activate() {
        this.status = Status.ACTIVE;
    }

    public void expire() {
        this.status = Status.EXPIRED;
    }

    public static int calculateEntryLimit(int currentEntries) {
        return MAX_QUEUE_SIZE - currentEntries;
    }
}
