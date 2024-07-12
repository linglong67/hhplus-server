package io.hhplus.server.infrastructure.queue;

import io.hhplus.server.domain.queue.Queue;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "queue")
@NoArgsConstructor
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String token;

    @Enumerated(EnumType.STRING)
    private Queue.Status status;

    private LocalDateTime activatedAt;

    public static QueueEntity from(Queue queue) {
        QueueEntity entity = new QueueEntity();
        entity.id = queue.getId();
        entity.userId = queue.getUserId();
        entity.token = queue.getToken();
        entity.status = queue.getStatus();
        entity.activatedAt = queue.getActivatedAt();

        return entity;
    }

    public static Queue toDomain(QueueEntity entity) {
        return Queue.builder()
                    .id(entity.id)
                    .userId(entity.userId)
                    .token(entity.token)
                    .status(entity.status)
                    .activatedAt(entity.activatedAt)
                    .build();
    }
}
