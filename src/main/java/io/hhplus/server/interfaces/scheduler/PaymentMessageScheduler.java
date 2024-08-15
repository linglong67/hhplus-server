package io.hhplus.server.interfaces.scheduler;

import io.hhplus.server.domain.payment.message.PaymentMessageProducer;
import io.hhplus.server.domain.payment.message.PaymentOutbox;
import io.hhplus.server.domain.payment.message.PaymentOutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentMessageScheduler {
    private final PaymentOutboxService paymentOutboxService;
    private final PaymentMessageProducer paymentMessageProducer;

    @Scheduled(fixedDelay = 60 * 1000)
    public void resendSuccessMessage() {
        List<PaymentOutbox> messagesForRetry = paymentOutboxService.getMessagesForRetry();
        messagesForRetry.forEach(v -> paymentMessageProducer.sendSuccessMessage(v.getPaymentId()));
    }
}
