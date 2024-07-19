package io.hhplus.server.application.payment;

import io.hhplus.server.domain.payment.Payment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentDto {
    private long id;
    private long reservationId;
    private long userId;
    private String status;
    private int paidPrice;

    public Payment toDomain() {
        return Payment.builder()
                      .id(id)
                      .reservationId(reservationId)
                      .userId(userId)
                      .paidPrice(paidPrice)
                      .build();
    }

    public static PaymentDto toDto(Payment payment) {
        return PaymentDto.builder()
                         .id(payment.getId())
                         .reservationId(payment.getReservationId())
                         .userId(payment.getUserId())
                         .status(payment.getStatus().name())
                         .paidPrice(payment.getPaidPrice())
                         .build();
    }
}
