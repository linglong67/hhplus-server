package io.hhplus.server.domain.payment;

import io.hhplus.server.domain.reservation.Reservation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PaymentSuccessEvent {
    private final Payment payment;
    private final Reservation reservation;
}
