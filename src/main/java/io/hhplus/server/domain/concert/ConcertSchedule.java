package io.hhplus.server.domain.concert;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConcertSchedule {
    private Long id;
    private Long concertId;
    private Long placeId;
    private LocalDateTime concertDatetime;
}
