package io.hhplus.server.interfaces.presentation.concert.dto;

import io.hhplus.server.application.concert.ConcertScheduleDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConcertScheduleResponse {
    private long concertScheduleId;
    private long placeId;
    private LocalDateTime concertDatetime;

    public static ConcertScheduleResponse toResponse(ConcertScheduleDto dto) {
        return ConcertScheduleResponse.builder()
                                      .concertScheduleId(dto.getConcertScheduleId())
                                      .placeId(dto.getPlaceId())
                                      .concertDatetime(dto.getConcertDatetime())
                                      .build();
    }
}
