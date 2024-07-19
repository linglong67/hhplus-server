package io.hhplus.server.interfaces.presentation.payment.dto;

import io.hhplus.server.application.payment.PaymentDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {
    private long reservationId;
    private long userId;
    private String status;
    private int paidPrice;

    public static PaymentResponse toResponse(PaymentDto dto) {
        return PaymentResponse.builder()
                              .reservationId(dto.getReservationId())
                              .userId(dto.getUserId())
                              .status(dto.getStatus())
                              .paidPrice(dto.getPaidPrice())
                              .build();
    }
}