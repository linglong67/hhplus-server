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

    public void generate(long userId) {
        this.userId = userId;
        this.token = UUID.randomUUID().toString();
        this.status = Status.WAITING;
    }
}
