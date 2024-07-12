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
    private Status status;

    public enum Status {
        RESERVED,
        PAID,
        CANCELED
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}