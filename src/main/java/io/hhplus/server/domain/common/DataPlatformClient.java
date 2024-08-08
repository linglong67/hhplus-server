package io.hhplus.server.domain.common;

import io.hhplus.server.domain.payment.Payment;
import io.hhplus.server.domain.reservation.Reservation;

public interface DataPlatformClient {
    void sendReservationResult(Payment payment, Reservation reservation);
}
