package io.hhplus.server.domain.payment.message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentOutboxRepository {
    PaymentOutbox save(PaymentOutbox paymentOutbox);

    Optional<PaymentOutbox> findByPaymentId(long paymentId);

    List<PaymentOutbox> getMessagesForRetry(String status, LocalDateTime validationTime);
}
