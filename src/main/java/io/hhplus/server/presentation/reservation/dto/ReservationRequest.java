package io.hhplus.server.presentation.reservation.dto;

import java.util.List;

public record ReservationRequest(
        long userId,
        List<Long> seatIds
) {
}
