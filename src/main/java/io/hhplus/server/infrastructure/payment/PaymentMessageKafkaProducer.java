package io.hhplus.server.infrastructure.payment;

import io.hhplus.server.domain.payment.message.PaymentMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMessageKafkaProducer implements PaymentMessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendSuccessMessage(Long paymentId) {
        kafkaTemplate.send("payment-success-topic", String.valueOf(paymentId));
    }
}
