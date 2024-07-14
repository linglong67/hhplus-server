package io.hhplus.server.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment) {
        payment.updateStatus(Payment.Status.PAID);
        return paymentRepository.save(payment);
    }
}
