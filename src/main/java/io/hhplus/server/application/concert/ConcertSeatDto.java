package io.hhplus.server.application.concert;

import io.hhplus.server.domain.concert.ConcertSeat;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertSeatDto {
    private long concertSeatId;
    private long concertScheduleId;
    private long seatId;
    private String status;
    private int price;

    public static ConcertSeatDto toDto(ConcertSeat concertSeat) {
        return ConcertSeatDto.builder()
                             .concertSeatId(concertSeat.getId())
                             .concertScheduleId(concertSeat.getConcertScheduleId())
                             .seatId(concertSeat.getSeatId())
                             .status(concertSeat.getStatus().name())
                             .price(concertSeat.getPrice())
                             .build();
    }
}
