package io.hhplus.server.infrastructure.payment;

import io.hhplus.server.domain.payment.Payment;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@NoArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long reservationId;

    @Enumerated(EnumType.STRING)
    private Payment.Status status;

    private Integer paidPrice;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PaymentEntity from(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.id = payment.getId();
        entity.userId = payment.getUserId();
        entity.reservationId = payment.getReservationId();
        entity.status = payment.getStatus();
        entity.paidPrice = payment.getPaidPrice();
        entity.createdAt = payment.getCreatedAt();
        entity.updatedAt = payment.getUpdatedAt();

        return entity;
    }

    public static Payment toDomain(PaymentEntity entity) {
        return Payment.builder()
                      .id(entity.id)
                      .userId(entity.userId)
                      .reservationId(entity.reservationId)
                      .status(entity.status)
                      .paidPrice(entity.paidPrice)
                      .createdAt(entity.createdAt)
                      .updatedAt(entity.updatedAt)
                      .build();
    }
}
