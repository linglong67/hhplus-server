package io.hhplus.server.domain.concert;

import lombok.Getter;

@Getter
public class ConcertSeat {
    private Long id;
    private Long concertScheduleId;
    private Long placeId;
    private Long seatId;
    private Long seatNo;
    private Status status;
    private Long price;

    public enum Status {
        AVAILABLE,
        OCCUPIED
    }

    public void release() {
        this.status = Status.AVAILABLE;
    }
}
