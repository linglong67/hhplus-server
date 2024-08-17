package io.hhplus.server.application.payment;

import io.hhplus.server.domain.payment.Payment;
import io.hhplus.server.domain.payment.PaymentService;
import io.hhplus.server.domain.payment.event.PaymentEventPublisher;
import io.hhplus.server.domain.payment.event.PaymentSuccessEvent;
import io.hhplus.server.domain.queue.QueueService;
import io.hhplus.server.domain.reservation.Reservation;
import io.hhplus.server.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentFacade {
    private final PaymentService paymentService;
    private final QueueService queueService;
    private final ReservationService reservationService;
    private final PaymentEventPublisher paymentEventPublisher;

    // 결제
    @Transactional
    public PaymentDto payment(String token, PaymentDto dto) {
        Reservation reservation = reservationService.updateReservationStatus(dto.getReservationId());
        Payment payment = paymentService.savePayment(dto.toDomain());
        paymentEventPublisher.success(new PaymentSuccessEvent(payment, reservation));

        queueService.expireToken(token);
        return PaymentDto.toDto(payment);
    }
}
