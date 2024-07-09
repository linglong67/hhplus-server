package io.hhplus.server.presentation.reservation.dto;

import java.time.LocalDateTime;

public record ReservationResponse(
        long reservationId,
        LocalDateTime reservedAt,
        String status,
        long pendingPrice
) {
}
