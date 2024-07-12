package io.hhplus.server.domain.reservation;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Reservation {
    private Long id;
    private Long userId;
    private Long concertScheduleId;
    private List<Long> concertSeatIds;
}
