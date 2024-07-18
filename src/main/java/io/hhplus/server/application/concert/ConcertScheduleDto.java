package io.hhplus.server.application.concert;

import io.hhplus.server.domain.concert.ConcertSchedule;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConcertScheduleDto {
    private long concertScheduleId;
    private long placeId;
    private LocalDateTime concertDatetime;

    public static ConcertScheduleDto toDto(ConcertSchedule concertSchedule) {
        return ConcertScheduleDto.builder()
                                 .concertScheduleId(concertSchedule.getId())
                                 .placeId(concertSchedule.getPlaceId())
                                 .concertDatetime(concertSchedule.getConcertDatetime())
                                 .build();
    }
}
