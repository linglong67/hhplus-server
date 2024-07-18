package io.hhplus.server.infrastructure.payment;

import io.hhplus.server.domain.payment.Payment;
import io.hhplus.server.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJpaRepository paymentRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = PaymentEntity.from(payment);
        return PaymentEntity.toDomain(paymentRepository.save(entity));
    }
}
