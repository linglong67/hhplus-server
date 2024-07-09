package io.hhplus.server.presentation.payment.dto;

public record PaymentRequest(
        long userId,
        long reservationId,
        long totalPrice
) {
}
