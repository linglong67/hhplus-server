package io.hhplus.server.infrastructure.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentOutboxJpaRepository extends JpaRepository<PaymentOutboxEntity, Long> {
    Optional<PaymentOutboxEntity> findByPaymentId(long paymentId);

    List<PaymentOutboxEntity> findAllByStatusIsAndUpdatedAtBefore(String status, LocalDateTime validationTime);
}
