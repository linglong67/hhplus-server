package io.hhplus.server.application.payment;

import io.hhplus.server.domain.payment.Payment;
import io.hhplus.server.domain.payment.PaymentService;
import io.hhplus.server.domain.queue.QueueService;
import io.hhplus.server.domain.reservation.ReservationService;
import io.hhplus.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentFacade {
    private final PaymentService paymentService;
    private final QueueService queueService;
    private final UserService userService;
    private final ReservationService reservationService;

    // 결제
    @Transactional
    public PaymentDto payment(String token, PaymentDto dto) {
        userService.usePoint(dto.getUserId(), dto.getPaidPrice());
        Payment payment = paymentService.savePayment(dto.toDomain());
        reservationService.updateReservationStatus(dto.getReservationId());

        queueService.expireToken(token);
        return PaymentDto.toDto(payment);
    }
}
