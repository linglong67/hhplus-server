package io.hhplus.server.domain.common;

import io.hhplus.server.domain.payment.Payment;
import io.hhplus.server.domain.reservation.Reservation;

public interface DataPlatformClient {
    void sendPaymentResult(Long paymentId);
}
