package io.hhplus.server.application.payment;

import io.hhplus.server.domain.payment.Payment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentDto {
    private long reservationId;
    private long userId;
    private Payment.Status status;
    int paidPrice;

    public Payment toDomain() {
        return Payment.builder()
                      .reservationId(reservationId)
                      .userId(userId)
                      .status(status)
                      .paidPrice(paidPrice)
                      .build();
    }

    public static PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                         .reservationId(payment.getReservationId())
                         .status(payment.getStatus())
                         .paidPrice(payment.getPaidPrice())
                         .build();
    }
}
