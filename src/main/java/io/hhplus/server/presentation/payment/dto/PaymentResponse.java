package io.hhplus.server.presentation.payment.dto;

import io.hhplus.server.application.payment.PaymentDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {
    long reservationId;
    long userId;
    String status;
    int paidPrice;

    public static PaymentResponse toResponse(PaymentDto dto) {
        return PaymentResponse.builder()
                              .reservationId(dto.getReservationId())
                              .userId(dto.getUserId())
                              .status(dto.getStatus().name())
                              .build();
    }
}