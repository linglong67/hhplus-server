package io.hhplus.server.domain.concert;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertSeat {
    private Long id;
    private Long concertScheduleId;
    private Long seatId;
    private Status status;
    private Integer price;
    private Integer version;

    public enum Status {
        AVAILABLE,
        OCCUPIED
    }

    public void assign() {
        if (this.status == Status.OCCUPIED) {
            throw new BusinessException(ErrorCode.CONCERT_SEAT_ALREADY_OCCUPIED);
        }

        this.status = Status.OCCUPIED;
    }

    public void release() {
        this.status = Status.AVAILABLE;
    }
}
