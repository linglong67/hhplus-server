package io.hhplus.server.domain.concert;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertSeat {
    private Long id;
    private Long concertScheduleId;
    private Long seatId;
//    private Long seatNo;
    private Status status;
    private Integer price;

    public enum Status {
        AVAILABLE,
        OCCUPIED
    }

    public void assign() {
        this.status = Status.OCCUPIED;
    }

    public void release() {
        this.status = Status.AVAILABLE;
    }
}
