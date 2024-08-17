package io.hhplus.server.infrastructure.payment;

import io.hhplus.server.domain.payment.message.PaymentOutbox;
import io.hhplus.server.domain.payment.message.PaymentOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentOutboxRepositoryImpl implements PaymentOutboxRepository {
    private final PaymentOutboxJpaRepository paymentOutboxRepository;

    @Override
    public PaymentOutbox save(PaymentOutbox paymentOutbox) {
        PaymentOutboxEntity entity = PaymentOutboxEntity.from(paymentOutbox);
        return PaymentOutboxEntity.toDomain(paymentOutboxRepository.save(entity));
    }

    @Override
    public Optional<PaymentOutbox> findByPaymentId(long paymentId) {
        return paymentOutboxRepository.findByPaymentId(paymentId).map(PaymentOutboxEntity::toDomain);
    }

    @Override
    public List<PaymentOutbox> getMessagesForRetry(String status, LocalDateTime validationTime) {
        return paymentOutboxRepository.findAllByStatusIsAndUpdatedAtBefore(status, validationTime)
                                      .stream()
                                      .map(PaymentOutboxEntity::toDomain)
                                      .toList();
    }
}
