package io.hhplus.server.presentation.concert.dto;

import io.hhplus.server.application.concert.ConcertScheduleDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConcertScheduleResponse {
    private long concertScheduleId;
    private long placeId;
    private LocalDateTime concertDateTime;

    public static ConcertScheduleResponse toResponse(ConcertScheduleDto dto) {
        return ConcertScheduleResponse.builder()
                                      .concertScheduleId(dto.getConcertScheduleId())
                                      .placeId(dto.getPlaceId())
                                      .concertDateTime(dto.getConcertDateTime())
                                      .build();
    }
}
