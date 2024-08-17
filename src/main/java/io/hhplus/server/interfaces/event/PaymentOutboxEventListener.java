package io.hhplus.server.interfaces.event;

import io.hhplus.server.domain.payment.event.PaymentSuccessEvent;
import io.hhplus.server.domain.payment.message.PaymentMessageProducer;
import io.hhplus.server.domain.payment.message.PaymentOutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentOutboxEventListener {
    private final PaymentOutboxService paymentOutboxService;
    private final PaymentMessageProducer paymentMessageProducer;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handlePaymentSuccess(PaymentSuccessEvent event) {
        paymentOutboxService.savePaymentOutbox(event.getPayment());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void producePaymentSuccess(PaymentSuccessEvent event) {
        paymentMessageProducer.sendSuccessMessage(event.getPayment().getId());
    }
}
