package io.hhplus.server.interfaces.consumer;

import io.hhplus.server.domain.common.DataPlatformClient;
import io.hhplus.server.domain.payment.message.PaymentOutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentMessageConsumer {
    private final PaymentOutboxService paymentOutboxService;
    private final DataPlatformClient dataPlatformClient;

    @KafkaListener(topics = {"payment-success-topic"}, groupId = "payment-group")
    public void updatePaymentOutbox(ConsumerRecord<String, String> record) {
        Long paymentId = Long.parseLong(record.value());
        paymentOutboxService.updatePaymentOutbox(paymentId);
    }

    @KafkaListener(topics = {"payment-success-topic"}, groupId = "data-platform-group")
    public void sendPaymentResult(ConsumerRecord<String, String> record) {
        try {
            Long paymentId = Long.parseLong(record.value());
            dataPlatformClient.sendPaymentResult(paymentId);
        } catch (Exception e) {
            log.error("데이터 플랫폼에 예약 결과 전송 실패");
        }
    }
}
