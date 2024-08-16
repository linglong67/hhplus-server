package io.hhplus.server.integration;

import io.hhplus.server.domain.payment.message.PaymentMessageProducer;
import io.hhplus.server.domain.payment.message.PaymentOutbox;
import io.hhplus.server.domain.payment.message.PaymentOutboxRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class PaymentMessageIntegrationTest {
    @Autowired
    private PaymentMessageProducer paymentMessageProducer;

    @Autowired
    private PaymentOutboxRepository paymentOutboxRepository;

    @Test
    @DisplayName("결제 성공 메시지 발행 시, Outbox 테이블 - PUBLISHED 상태로 업데이트 (발행 보장)")
    void guarantee_produce_success_message() {
        long paymentId = 15L;
        paymentMessageProducer.sendSuccessMessage(paymentId);

        await().atMost(Duration.ofMillis(10 * 1000)).untilAsserted(() -> {
            PaymentOutbox paymentOutbox = paymentOutboxRepository.findByPaymentId(paymentId).get();
            Duration duration = Duration.between(paymentOutbox.getUpdatedAt(), LocalDateTime.now());

            assertThat(paymentOutbox.getStatus()).isEqualTo(PaymentOutbox.Status.PUBLISHED);
            assertThat(duration.getSeconds()).isLessThan(15);
        });
    }

    @Test
    @DisplayName("결제 성공 메시지 발행했지만, Outbox 테이블에 존재하지 않는 경우")
    void fail_produce_success_message() {
        long paymentId = 10000L;
        paymentMessageProducer.sendSuccessMessage(paymentId);

        await().atMost(Duration.ofMillis(3 * 1000)).untilAsserted(() -> {
            assertThat(paymentOutboxRepository.findByPaymentId(paymentId)).isNotPresent();
        });
    }
}
