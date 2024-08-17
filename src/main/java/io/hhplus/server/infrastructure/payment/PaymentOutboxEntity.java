package io.hhplus.server.infrastructure.payment;

import io.hhplus.server.domain.payment.message.PaymentOutbox;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_outbox")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PaymentOutboxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentOutbox.Status status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static PaymentOutboxEntity from(PaymentOutbox paymentOutbox) {
        PaymentOutboxEntity entity = new PaymentOutboxEntity();
        entity.id = paymentOutbox.getId();
        entity.paymentId = paymentOutbox.getPaymentId();
        entity.status = paymentOutbox.getStatus();
        entity.createdAt = paymentOutbox.getCreatedAt();
        entity.updatedAt = paymentOutbox.getUpdatedAt();

        return entity;
    }

    public static PaymentOutbox toDomain(PaymentOutboxEntity entity) {
        return PaymentOutbox.builder()
                            .id(entity.id)
                            .paymentId(entity.paymentId)
                            .status(entity.status)
                            .createdAt(entity.createdAt)
                            .updatedAt(entity.updatedAt)
                            .build();
    }
}
