package io.hhplus.server.presentation.payment.dto;

import io.hhplus.server.application.payment.PaymentDto;
import lombok.Getter;

@Getter
public class PaymentRequest {
    private long reservationId;
    private long userId;
    private int paidPrice;

    public static PaymentDto toDto(PaymentRequest request) {
        return PaymentDto.builder()
                         .reservationId(request.getReservationId())
                         .userId(request.getUserId())
                         .paidPrice(request.getPaidPrice())
                         .build();
    }
}
