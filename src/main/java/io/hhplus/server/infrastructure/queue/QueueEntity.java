package io.hhplus.server.infrastructure.queue;

import io.hhplus.server.domain.queue.Queue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "queue")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String token;

    @Enumerated(EnumType.STRING)
    private Queue.Status status;

    private LocalDateTime activatedAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static QueueEntity from(Queue queue) {
        QueueEntity entity = new QueueEntity();
        entity.id = queue.getId();
        entity.userId = queue.getUserId();
        entity.token = queue.getToken();
        entity.status = queue.getStatus();
        entity.activatedAt = queue.getActivatedAt();
        entity.createdAt = queue.getCreatedAt();
        entity.updatedAt = queue.getUpdatedAt();

        return entity;
    }

    public static Queue toDomain(QueueEntity entity) {
        return Queue.builder()
                    .id(entity.id)
                    .userId(entity.userId)
                    .token(entity.token)
                    .status(entity.status)
                    .activatedAt(entity.activatedAt)
                    .createdAt(entity.createdAt)
                    .updatedAt(entity.updatedAt)
                    .build();
    }
}
