package io.hhplus.server.application.concert;

import io.hhplus.server.domain.concert.ConcertSeat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConcertSeatDto {
    private long concertSeatId;
    private long concertScheduleId;
    private long placeId;
    private long seatId;
    private long seatNo;
    private ConcertSeat.Status status;
    private long price;
    private LocalDateTime concertDateTime;

    public static ConcertSeatDto toDto(ConcertSeat concertSeat) {
        return ConcertSeatDto.builder()
                             .concertSeatId(concertSeat.getId())
                             .concertScheduleId(concertSeat.getConcertScheduleId())
                             .seatId(concertSeat.getSeatId())
                             .seatNo(concertSeat.getSeatNo())
                             .status(concertSeat.getStatus())
                             .price(concertSeat.getPrice())
                             .build();
    }
}
