package io.hhplus.server.domain.payment.message;

public interface PaymentMessageProducer {
    void sendSuccessMessage(Long paymentId);
}
