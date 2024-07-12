package io.hhplus.server.domain.concert;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ConcertSchedule {
    private Long id;
    private Long concertId;
    private Long placeId;
    private LocalDateTime concertDateTime;
}
