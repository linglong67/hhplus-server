package io.hhplus.server.presentation.concert.dto;

public record ConcertSeatResponse(
        long seatId,
        long seatNum,
        long price
) {
}
