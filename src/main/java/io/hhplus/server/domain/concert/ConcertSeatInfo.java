package io.hhplus.server.domain.concert;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertSeatInfo {
    private Long concertSeatId;
    private Integer price;

    private Long seatId;
    private Integer seatNo;

    public static ConcertSeatInfo createConcertSeatInfo(ConcertSeat concertSeat, Place.Seat seat) {
        return ConcertSeatInfo.builder()
                              .concertSeatId(concertSeat.getId())
                              .price(concertSeat.getPrice())
                              .seatId(seat.getId())
                              .seatNo(seat.getSeatNo())
                              .build();
    }
}
