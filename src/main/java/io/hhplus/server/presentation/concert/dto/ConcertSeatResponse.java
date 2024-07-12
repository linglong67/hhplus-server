package io.hhplus.server.presentation.concert.dto;

import io.hhplus.server.application.concert.ConcertSeatDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertSeatResponse {
    private long concertSeatId;
    private long placeId;
    private long seatId;
    private long seatNo;
    private long price;

    public static ConcertSeatResponse toResponse(ConcertSeatDto dto) {
        return ConcertSeatResponse.builder()
                                  .concertSeatId(dto.getConcertSeatId())
                                  .placeId(dto.getPlaceId())
                                  .seatId(dto.getSeatId())
                                  .seatNo(dto.getSeatNo())
                                  .price(dto.getPrice())
                                  .build();
    }
}
