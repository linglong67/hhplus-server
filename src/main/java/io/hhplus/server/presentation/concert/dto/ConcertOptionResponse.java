package io.hhplus.server.presentation.concert.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ConcertOptionResponse(
        long concertOptionId,
        LocalDate concertDate,
        LocalTime startTime
) {
}
