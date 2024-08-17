package io.hhplus.server.domain.payment.message;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
import io.hhplus.server.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentOutboxService {
    private final PaymentOutboxRepository paymentOutboxRepository;

    public void savePaymentOutbox(Payment payment) {
        paymentOutboxRepository.save(
                PaymentOutbox.builder().paymentId(payment.getId()).status(PaymentOutbox.Status.INIT).build());
    }

    public void updatePaymentOutbox(Long paymentId) {
        Long id = paymentOutboxRepository.findByPaymentId(paymentId)
                                         .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_OUTBOX_NOT_FOUND))
                                         .getId();

        paymentOutboxRepository.save(
                PaymentOutbox.builder().id(id).paymentId(paymentId).status(PaymentOutbox.Status.PUBLISHED).build());
    }

    public List<PaymentOutbox> getMessagesForRetry() {
        return paymentOutboxRepository.getMessagesForRetry(
                PaymentOutbox.Status.INIT.name(), LocalDateTime.now().minusMinutes(3));
    }
}
