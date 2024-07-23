package io.hhplus.server.interfaces.presentation.concert.dto;

import io.hhplus.server.application.concert.ConcertSeatDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertSeatResponse {
    private long concertSeatId;
    private long concertScheduleId;
    private long seatId;
    private String status;
    private int price;

    public static ConcertSeatResponse toResponse(ConcertSeatDto dto) {
        return ConcertSeatResponse.builder()
                                  .concertSeatId(dto.getConcertSeatId())
                                  .concertScheduleId(dto.getConcertScheduleId())
                                  .seatId(dto.getSeatId())
                                  .status(dto.getStatus())
                                  .price(dto.getPrice())
                                  .build();
    }
}
