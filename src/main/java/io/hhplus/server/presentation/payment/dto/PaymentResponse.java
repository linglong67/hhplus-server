package io.hhplus.server.presentation.payment.dto;

import java.time.LocalDateTime;

public record PaymentResponse(
        long paymentId,
        LocalDateTime paidAt,
        String status
) {
}