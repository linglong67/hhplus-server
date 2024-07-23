package io.hhplus.server.domain.payment;

import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);

    Optional<Payment> getPayment(Long paymentId);
}
